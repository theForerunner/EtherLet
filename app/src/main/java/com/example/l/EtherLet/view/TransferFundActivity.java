package com.example.l.EtherLet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.FriendDTO;
import com.example.l.EtherLet.presenter.FriendListPresenter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransferFundActivity extends AppCompatActivity implements FriendListInterface {
    private RecyclerView friendListRecyclerView;
    //private BottomSheetDialog sendMoneyBottomSheet;
    private FriendAdapter friendAdapter;
    private FriendListPresenter friendListPresenter;
    private CardView friendCard;
    private Button scanButton;
    private GlobalData globalData;
    //private String toAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_fund);

        globalData = (GlobalData) getApplication();

        friendListPresenter = new FriendListPresenter(TransferFundActivity.this);
        friendListRecyclerView=findViewById(R.id.friendlist_recycler_View);
        friendListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendAdapter=new FriendAdapter(initDefaultFriendList());
        friendListRecyclerView.setAdapter(friendAdapter);
        if (globalData.getPrimaryUser().getUserId() != 0) {
            friendListPresenter.loadFriendList(TransferFundActivity.this, globalData.getPrimaryUser().getUserId());
        }

        //friendListPresenter.loadFriendList(TransferFundActivity.this);

        //setUpSendMoneyBottomSheet();

        scanButton=findViewById(R.id.scan_button);

        scanButton.setOnClickListener(v -> codeScan());
    }

    @Override
    public void showFriendList(List<FriendDTO> friendDTOList) {
        friendAdapter = new FriendAdapter(friendDTOList);
        friendListRecyclerView.setAdapter(friendAdapter);
    }

    private class FriendHolder extends RecyclerView.ViewHolder{
        private FriendDTO mFriend;
        private CircleImageView thumbnail;
        private TextView username;

        private FriendHolder(View itemView){
            super(itemView);
            thumbnail=itemView.findViewById(R.id.friend_image);
            username=itemView.findViewById(R.id.friend_name);
        }

        private void bindFriend(FriendDTO friend){
            mFriend=friend;
            //thumbnail.setImageBitmap();
            username.setText(mFriend.getUserUsername());

            friendCard.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("friend address", mFriend.getUserKey());
                setResult(RESULT_OK, intent);
                finish();
            });
        }
    }

    private class FriendAdapter extends RecyclerView.Adapter<FriendHolder>{
        private List<FriendDTO> friendList;

        FriendAdapter(List<FriendDTO> friendList){
            this.friendList=friendList;
        }

        @Override
        public FriendHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            LayoutInflater layoutInflater=LayoutInflater.from(TransferFundActivity.this);
            View view=layoutInflater.inflate(R.layout.transfer_friendlist_item,viewGroup,false);
            friendCard=view.findViewById(R.id.friend_card);
            return new FriendHolder(view);
        }

        @Override
        public void onBindViewHolder(FriendHolder friendHolder,int i){
            FriendDTO friend=friendList.get(i);
            friendHolder.bindFriend(friend);
        }

        @Override
        public int getItemCount(){
            return friendList.size();
        }
    }

    @Override
    public void showFailureMessage() {
            Toast.makeText(TransferFundActivity.this, "Network Error", Toast.LENGTH_LONG).show();
    }



    private List<FriendDTO> initDefaultFriendList(){
        List<FriendDTO> friendList=new ArrayList<>();
        FriendDTO friend=new FriendDTO(00,00,"John Smith","1234");
        for(int i=0;i<10;i++){
            friendList.add(friend);
        }
        return friendList;
    }



    public void codeScan() {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CodeScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Log.i("DT","二维码识别失败");
                return;
            } else {
                String ScanResult = intentResult.getContents();
                Log.i("DT",ScanResult);
                String toAddress=ScanResult;
                Intent intent = new Intent();
                intent.putExtra("friend address", toAddress);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
