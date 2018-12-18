package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.dto.CommentDTO;

import java.util.List;

public interface CommentListViewInterface {
    void showCommentList(List<CommentDTO> commentDTOList);

    void showFailureMessage();
}
