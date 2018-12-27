package com.example.l.EtherLet.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.DBHelper;
import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.presenter.UserDetailPresenter;

public class UserDetailActivity extends AppCompatActivity implements UserDetailViewInterface {

    GlobalData globalData;
    DBHelper dbHelper;
    private UserDetailPresenter userDetailPresenter;
    private ImageView image;
    private TextView textId;

    private static final int PHOTO_REQUEST_GALLERY = 1;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 2;// 结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this,"EtherLet.db",null,1);
        setContentView(R.layout.user_detail_layout);

        globalData = (GlobalData) getApplication();
        userDetailPresenter=new UserDetailPresenter(UserDetailActivity.this);
        image=findViewById(R.id.user_image_detail);
        textId=findViewById(R.id.user_id_detail);
        textId.setText(globalData.getPrimaryUser().getUserUsername().toString());

        //image init undone

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PHOTO_REQUEST_GALLERY);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PHOTO_REQUEST_GALLERY){
            if(data!=null){
                Uri uri=data.getData();
                crop(uri);
            }
        }
        else if(requestCode==PHOTO_REQUEST_CUT){
            if(data!=null){
                Bitmap bitmap=data.getParcelableExtra("data");//this is the image you get

                userDetailPresenter.upLoadImage();//upLoad Undone

                this.image.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void showInfo(int type){
        if(type==0){
            Toast.makeText(UserDetailActivity.this, "Image Upload Success", Toast.LENGTH_SHORT).show();
        }
        else if(type==1){
            Toast.makeText(UserDetailActivity.this, "Info Edit Success", Toast.LENGTH_SHORT).show();
        }
    }
}