package com.example.l.EtherLet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.FriendDTO;
import com.example.l.EtherLet.presenter.FriendListPresenter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListActivity extends AppCompatActivity implements FriendListInterface {
    private RecyclerView friendListRecyclerView;
    private BottomSheetDialog searchNameBottomSheet;
    private FriendAdapter friendAdapter;
    private FriendListPresenter friendListPresenter;
    private CardView friendCard;
    private Button newFriendButton;
    private GlobalData globalData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        globalData = (GlobalData) getApplication();

        friendListPresenter = new FriendListPresenter(FriendListActivity.this);
        friendListRecyclerView=findViewById(R.id.friendlist_recycler_View);
        friendListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendAdapter=new FriendAdapter(initDefaultFriendList());
        friendListRecyclerView.setAdapter(friendAdapter);
        if (globalData.getPrimaryUser().getUserId() != 0) {
            friendListPresenter.loadFriendList(FriendListActivity.this, globalData.getPrimaryUser().getUserId());
        }

        newFriendButton=findViewById(R.id.new_friend_button);
        setUpSearchNameBottomSheet();

        newFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNameBottomSheet.show();
                update();
            }
        });
    }

    private void update(){
        friendListPresenter.loadFriendList(this,globalData.getPrimaryUser().getUserId());
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
        private Button deleteFriendButton;

        private FriendHolder(View itemView){
            super(itemView);
            thumbnail=itemView.findViewById(R.id.friend_image);
            username=itemView.findViewById(R.id.friend_name);
            deleteFriendButton=itemView.findViewById(R.id.delete_friend_button);
        }

        private void bindFriend(FriendDTO friend){
            mFriend=friend;
            //thumbnail.setImageBitmap();
            username.setText(mFriend.getUserUsername());

            /*
            friendCard.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("friend address", mFriend.getUserKey());
                setResult(RESULT_OK, intent);
                finish();
            });
            */
            deleteFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendListPresenter.deleteFriend(FriendListActivity.this,mFriend.getFriendshipId());
                    update();
                }
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
            LayoutInflater layoutInflater=LayoutInflater.from(FriendListActivity.this);
            View view=layoutInflater.inflate(R.layout.friendlist_item,viewGroup,false);
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
        Toast.makeText(FriendListActivity.this, "Network Error", Toast.LENGTH_LONG).show();
    }



    private List<FriendDTO> initDefaultFriendList(){
        List<FriendDTO> friendList=new ArrayList<>();
        FriendDTO friend=new FriendDTO(00,00,"John Smith","1234");
        for(int i=0;i<10;i++){
            friendList.add(friend);
        }
        return friendList;
    }

    private void setUpSearchNameBottomSheet(){
        searchNameBottomSheet=new BottomSheetDialog(this);
        View bottomSheetView=LayoutInflater.from(this).inflate(R.layout.send_money_layout,null);
        searchNameBottomSheet.setContentView(bottomSheetView);
        searchNameBottomSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        searchNameBottomSheet.setCancelable(true);
        searchNameBottomSheet.setCanceledOnTouchOutside(true);

        EditText enterId=bottomSheetView.findViewById(R.id.enter_number);
        Button confirm=bottomSheetView.findViewById(R.id.confirm_sending);

        confirm.setOnClickListener(v -> {
            friendListPresenter.addFriend(this,Integer.parseInt(enterId.getText().toString()),globalData.getPrimaryUser().getUserId());
            searchNameBottomSheet.cancel();
        });
    }
}
