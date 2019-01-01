package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.dto.FriendDTO;

import java.util.List;

public interface FriendListInterface {
    public void showFailureMessage();
    public void showFriendList(List<FriendDTO> friendDTOList);
}
