package com.example.l.EtherLet.model;

import android.content.Context;

interface FriendListInterface {
    void loadFriendListData(final FriendList.LoadDataCallBack callBack, Context context, int user_id);
}
