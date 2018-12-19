package com.example.l.EtherLet.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class UserBusiness implements UserInterface {
    @Override
    public void login(final OnLoginListener listener, final LocalCallBack callBack, Context context, Map<String, Object> map) {
        /*
        if (userAccount.equals("Robbin") && password.equals("1234")) {//模拟成功
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(password);
            listener.loginSuccess(user);
        } else {
            listener.loginFail();
        }*/
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.user_login_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 200) {
                        callBack.onRemoteLoginSuccess(jsonObject);
                    } else {
                        callBack.onRemoteLoginFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                callBack.onRemoteLoginFailure();
            }
        });
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
    public void registration(final OnRegistrationListener listener, final LocalCallBack callBack, Context context, Map<String, Object> map) {

        /*
        boolean registrationResult=true;//模拟成功
        if(registrationResult){
            listener.registrationSuccess();
        }
        else{
            listener.registrationFail();
        }*/

        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.add_user_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onRemoteRegisterSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onRemoteRegisterFailure();
            }
        });

    }

    public interface LocalCallBack {
        void onRemoteRegisterSuccess(JSONObject jsonObject);

        void onRemoteRegisterFailure();

        void onRemoteLoginSuccess(JSONObject jsonObject);

        void onRemoteLoginFailure();
    }

}
