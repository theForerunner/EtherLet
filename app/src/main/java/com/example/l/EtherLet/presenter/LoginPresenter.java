package com.example.l.EtherLet.presenter;

import com.example.l.EtherLet.model.OnLoginListener;
import com.example.l.EtherLet.model.UserBusiness;
import com.example.l.EtherLet.model.UserInterface;
import com.example.l.EtherLet.model.UserModel;
import com.example.l.EtherLet.view.LoginViewInterface;

import java.util.ArrayList;


public class LoginPresenter {
    private UserInterface userBusiness;
    private LoginViewInterface loginView;

    public LoginPresenter(LoginViewInterface loginView){
        this.loginView=loginView;
        this.userBusiness=new UserBusiness();
    }

    public void Login(){
        userBusiness.login(loginView.getUserId(), loginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(UserModel user) {
                loginView.enterMainActivity(user);

            }

            @Override
            public void loginFail() {
                loginView.showFail();

            }
        });
    }
    public void clear(){
        loginView.clearUserId();
        loginView.clearPassword();
    }

    public ArrayList getHistoryList(){
        return userBusiness.getUserHistory();
    }

}
