package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.PostList;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.PostListViewInterface;

import org.json.JSONObject;

public class PostPresenter implements PostPresenterInterface, PostList.LoadDataCallBack {
    private final PostListViewInterface postListViewInterface;
    private final PostList postList;

    public PostPresenter(PostListViewInterface postListViewInterface) {
        this.postListViewInterface = postListViewInterface;
        this.postList = new PostList();
    }

    @Override
    public void loadPostList(Context context) {
        postList.getPostListData(this, context);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        postListViewInterface.showPostList(JSONParser.parseJsonToPostList(jsonObject));
    }

    @Override
    public void onFailure() {
        postListViewInterface.showFailureMessage();
    }
}
