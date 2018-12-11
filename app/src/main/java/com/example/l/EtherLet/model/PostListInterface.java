package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.Map;

public interface PostListInterface {
    void getPostListData(PostList.LoadDataCallBack callBack, Context context);

    void addNewPost(PostList.LoadDataCallBack callBack, Context context, Map<String, Object> map);

}
