package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.Map;

interface FriendListInterface {
    void loadFriendListData(final FriendList.LoadDataCallBack callBack, Context context, int user_id);
    void deleteFriend(final FriendList.LoadDataCallBack callback,Context context, int friendship_id);
    void addFriend(final FriendList.LoadDataCallBack  callback,Context context, Map<String,Object> map);
}
