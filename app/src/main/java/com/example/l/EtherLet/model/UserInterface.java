package com.example.l.EtherLet.model;

import java.util.ArrayList;

public interface UserInterface {
    void login(String userId, String password, OnLoginListener listener);
    ArrayList getUserHistory();
    void registration(String id, String password, OnRegistrationListener listener);
}
