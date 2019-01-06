package com.example.l.EtherLet;

import android.app.Application;

import com.example.l.EtherLet.model.dto.User;

//用于全局数据的存放
public class GlobalData extends Application {
    private User primaryUser;

    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }
}
