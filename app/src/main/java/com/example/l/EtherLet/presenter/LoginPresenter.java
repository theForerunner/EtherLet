package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.OnLoginListener;
import com.example.l.EtherLet.model.UserBusiness;
import com.example.l.EtherLet.model.UserInterface;
import com.example.l.EtherLet.model.dto.User;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.LoginViewInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class LoginPresenter implements UserBusiness.LocalCallBack{
    private UserInterface userBusiness;
    private LoginViewInterface loginView;

    public LoginPresenter(LoginViewInterface loginView){
        this.loginView=loginView;
        this.userBusiness=new UserBusiness();
    }

    public void Login(Context context, Map<String, Object> map){
        userBusiness.login(new OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                loginView.enterMainActivity(user);

            }

            @Override
            public void loginFail() {
                loginView.showFail();

            }
        }, this, context, map);
    }
    public void clear(){
        loginView.clearUserId();
        loginView.clearPassword();
    }

    public ArrayList getHistoryList(){
        return userBusiness.getUserHistory();
    }

    @Override
    public void onRemoteRegisterFailure() {

    }

    @Override
    public void onRemoteRegisterSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onRemoteLoginSuccess(JSONObject jsonObject) {
        loginView.enterMainActivity(JSONParser.parseJsonToUser(jsonObject));
    }

    @Override
    public void onRemoteLoginFailure() {
        loginView.showFail();
    }
}
