package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.OnRegistrationListener;
import com.example.l.EtherLet.model.UserBusiness;
import com.example.l.EtherLet.model.UserInterface;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.RegistrationViewInterface;

import org.json.JSONObject;

import java.util.Map;

public class RegistrationPresenter implements UserBusiness.LocalCallBack {
    private UserInterface userBusiness;
    private RegistrationViewInterface registrationView;
    public RegistrationPresenter(RegistrationViewInterface registrationView){
        this.registrationView=registrationView;
        this.userBusiness=new UserBusiness();
    }
    public void registration(Context context, Map<String, Object> map){
        userBusiness.registration(new OnRegistrationListener() {
            @Override
            public void registrationSuccess() {
                //registrationView.registrationSuccess();
            }

            @Override
            public void registrationFail() {

                //registrationView.registrationFail();
            }
        }, this,  context, map);
    }

    @Override
    public void onRemoteRegisterSuccess(JSONObject jsonObject) {
        registrationView.registrationSuccess(JSONParser.parseJsonToUser(jsonObject));
    }

    @Override
    public void onRemoteRegisterFailure() {
        registrationView.registrationFail();
    }

    @Override
    public void onRemoteLoginSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onRemoteLoginFailure() {

    }
}
