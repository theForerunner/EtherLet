package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.dto.PostDTO;

import java.util.List;

public interface PostListViewInterface {
    void showPostList(List<PostDTO> postDTOList);

    void showFailureMessage();
}
