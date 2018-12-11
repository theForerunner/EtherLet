package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.Post;

import java.util.List;

public interface PostListViewInterface {
    void showPostList(List<Post> postList);

    void showFailureMessage();
}
