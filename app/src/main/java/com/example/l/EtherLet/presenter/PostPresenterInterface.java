package com.example.l.EtherLet.presenter;

import android.content.Context;

import java.util.Map;

public interface PostPresenterInterface {
    void loadPostList(Context context);

    void addNewPost(Context context, Map<String, Object> map);
}
