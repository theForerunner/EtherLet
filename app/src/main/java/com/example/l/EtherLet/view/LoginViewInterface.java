package com.example.l.EtherLet.view;


import com.example.l.EtherLet.model.User;

public interface LoginViewInterface {
    String getUserId();
    String getPassword();
    void clearUserId();
    void clearPassword();
    void enterMainActivity(User user);
    void showFail();
}
