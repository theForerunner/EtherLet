package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.FriendList;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.FriendListInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendListPresenter implements FriendList.LoadDataCallBack{
    private FriendListInterface friendListInterface;
    private FriendList friendList;

    public FriendListPresenter(FriendListInterface friendListInterface){
        this.friendListInterface = friendListInterface;
        friendList=new FriendList();
    }


    public void loadFriendList(Context context, int user_id) {
        friendList.loadFriendListData(this, context, user_id);
    }

    public void deleteFriend(Context context,int friendship_id){
        friendList.deleteFriend(this,context,friendship_id);
    }

    public void addFriend(Context context,int friend_id,int user_id){
        Map<String, Object> map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("friend_id",friend_id);
        friendList.addFriend(this,context,map);
    }

    @Override
    public void onLoadListSuccess(JSONObject jsonObject) {
        friendListInterface.showFriendList(JSONParser.parseJsonToFriendList(jsonObject));
    }

    @Override
    public void onDeleteSuccess(JSONObject jsonObject){

    }

    @Override
    public void onAddSuccess(JSONObject jsonObject){

    }

    @Override
    public void onFailure() {
        //friendListInterface.showFailureMessage();
    }
}
