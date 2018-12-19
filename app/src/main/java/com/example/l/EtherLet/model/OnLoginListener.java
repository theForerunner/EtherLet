package com.example.l.EtherLet.model;

import com.example.l.EtherLet.model.dto.User;

public interface OnLoginListener {
    void loginSuccess(User user);
    void loginFail();
}
