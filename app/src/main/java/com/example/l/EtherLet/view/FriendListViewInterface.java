package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.dto.FriendDTO;

import java.util.List;

public interface FriendListViewInterface {
    public void showFailureMessage();
    public void showPostList(List<FriendDTO> friendDTOList);
}
