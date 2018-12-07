package com.example.l.EtherLet.view;


import com.example.l.EtherLet.model.UserModel;

public interface LoginViewInterface {
    String getUserId();
    String getPassword();
    void clearUserId();
    void clearPassword();
    void enterMainActivity(UserModel user);
    void showFail();
}
