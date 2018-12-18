package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.CommentList;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.CommentListViewInterface;

import org.json.JSONObject;

public class CommentPresenter implements CommentPresenterInterface, CommentList.LoadDataCallBack {
    private final CommentListViewInterface commentListViewInterface;
    private final CommentList commentList;

    public CommentPresenter(CommentListViewInterface commentListViewInterface) {
        this.commentListViewInterface = commentListViewInterface;
        this.commentList = new CommentList();
    }

    @Override
    public void loadCommentList(Context context) {
        commentList.getCommentList(this, context);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        commentListViewInterface.showCommentList(JSONParser.parseJsonToCommentList(jsonObject));
    }

    @Override
    public void onFailure() {
        commentListViewInterface.showFailureMessage();
    }
}
