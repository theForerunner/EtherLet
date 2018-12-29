package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.FriendList;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.FriendListViewActivity;
import com.example.l.EtherLet.view.FriendListViewInterface;

import org.json.JSONObject;

public class FriendListPresenter implements FriendList.LoadDataCallBack{
    private FriendListViewInterface friendListViewInterface;
    private FriendList friendList;

    public FriendListPresenter(FriendListViewActivity friendListViewActivity){
        this.friendListViewInterface=friendListViewActivity;
        friendList=new FriendList();
    }

    public void loadFriendList(Context context) {
        friendList.loadFriendListData(this, context);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        friendListViewInterface.showPostList(JSONParser.parseJsonToFriendList(jsonObject));
    }

    @Override
    public void onFailure() {
        //friendListViewInterface.showFailureMessage();
    }
}
