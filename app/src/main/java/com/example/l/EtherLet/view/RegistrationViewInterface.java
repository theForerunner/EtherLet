package com.example.l.EtherLet.view;


import com.example.l.EtherLet.model.dto.User;

public interface RegistrationViewInterface {
    String getAccount();
    String getPassword();
    void registrationSuccess(User user);
    void registrationFail();
}
