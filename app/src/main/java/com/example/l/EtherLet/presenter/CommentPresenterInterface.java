package com.example.l.EtherLet.presenter;

import android.content.Context;

import java.util.Map;

public interface CommentPresenterInterface {
    void loadCommentList(Context context, int post_id);

    void addComment(Context context, Map<String, Object> map);
}
