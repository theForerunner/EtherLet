package com.example.l.EtherLet.model;

import java.util.ArrayList;

public class UserBusiness implements UserInterface {
    @Override
    public void login(final String userId, final String password, final OnLoginListener listener) {

        if(userId.equals("Robbin")&&password.equals("1234")){//模拟成功
            UserModel user=new UserModel();
            user.setUserId(userId);
            user.setPassword(password);
            listener.loginSuccess(user);
        }else
        {
            listener.loginFail();
        }
    }


    @Override
    public ArrayList getUserHistory() {//sqlite demo
        ArrayList historyList=new ArrayList();
        historyList.add("Robbin");
        historyList.add("testUser1");
        historyList.add("testUser2");
        return historyList;

    }

    @Override
    public void registration(final String id,final String password,final OnRegistrationListener listener) {

        boolean registrationResult=true;//模拟成功
        if(registrationResult){
            listener.registrationSuccess();
        }
        else{
            listener.registrationFail();
        }
    }
}
