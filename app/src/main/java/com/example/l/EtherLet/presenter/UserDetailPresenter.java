package com.example.l.EtherLet.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.l.EtherLet.model.UserBusiness;
import com.example.l.EtherLet.model.UserInterface;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.UserDetailViewInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDetailPresenter implements UserDetailPresenterInterface,UserBusiness.DetailCallBack {
    private UserInterface userInterface;
    private UserDetailViewInterface userDetailViewInterface;

    public UserDetailPresenter(UserDetailViewInterface userDetailView){
        userDetailViewInterface=userDetailView;
        userInterface=new UserBusiness();
    }
    @Override
    public void upLoadImage(Context context, Bitmap bitmap, int user_id){
        userInterface.upLoadImage(this, context, bitmap, user_id);
    }

    @Override
    public void upLoadKey(Context context,Map<String,Object> map,int user_id){
        userInterface.upLoadKey(this,context,map,user_id);
    }

    @Override
    public void onUploadImageSuccess(JSONObject jsonObject){
        try {
            if (jsonObject.getInt("code") != 200) {
                userDetailViewInterface.showInfo(1);
            } else {
                userDetailViewInterface.showInfo(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUploadKeySuccess(JSONObject jsonObject){
        userDetailViewInterface.setUser(JSONParser.parseJsonToUser(jsonObject));
    }

    @Override
    public void onUploadKeyFailure(){

    }

    @Override
    public void onUploadImageFailure(){
        userDetailViewInterface.showInfo(1);
    }
}
