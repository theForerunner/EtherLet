package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.CommentList;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.CommentListViewInterface;

import org.json.JSONObject;

import java.util.Map;

public class CommentPresenter implements CommentPresenterInterface, CommentList.LoadDataCallBack {
    private final CommentListViewInterface commentListViewInterface;
    private final CommentList commentList;

    public CommentPresenter(CommentListViewInterface commentListViewInterface) {
        this.commentListViewInterface = commentListViewInterface;
        this.commentList = new CommentList();
    }

    @Override
    public void loadCommentList(Context context, int post_id) {
        commentList.loadCommentList(this, context, post_id);
    }

    @Override
    public void addComment(Context context, Map<String, Object> map) {
        commentList.addNewComment(this, context, map);
    }

    @Override
    public void onLoadCommentListSuccess(JSONObject jsonObject) {
        commentListViewInterface.showCommentList(JSONParser.parseJsonToCommentList(jsonObject));
    }

    @Override
    public void onLoadCommentListFailure() {
        commentListViewInterface.showFailureMessage();
    }

    @Override
    public void onAddNewCommentSuccess(JSONObject jsonObject) {
        commentListViewInterface.showCommentList(JSONParser.parseJsonToCommentList(jsonObject));
    }

    @Override
    public void onAddNewCommentFailure() {
        commentListViewInterface.showFailureMessage();
    }
}
