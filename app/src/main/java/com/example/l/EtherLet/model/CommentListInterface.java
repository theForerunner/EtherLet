package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.Map;

public interface CommentListInterface {
    void getCommentList(CommentList.LoadDataCallBack callBack, Context context);

    void addNewComment(CommentList.LoadDataCallBack callBack, Context context, Map<String, Object> map);
}
