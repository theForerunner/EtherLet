package com.example.l.EtherLet.view;


import com.example.l.EtherLet.model.dto.User;

public interface LoginViewInterface {
    String getAccount();
    String getPassword();
    void clearUserId();
    void clearPassword();
    void enterMainActivity(User user);
    void showFail();
}
