package com.example.l.EtherLet.presenter;

import com.example.l.EtherLet.model.UserBusiness;
import com.example.l.EtherLet.model.UserInterface;
import com.example.l.EtherLet.view.UserDetailViewInterface;

public class UserDetailPresenter implements UserDetailPresenterInterface,UserBusiness.DetailCallBack {
    private UserInterface userInterface;
    private UserDetailViewInterface userDetailViewInterface;

    public UserDetailPresenter(UserDetailViewInterface userDetailView){
        userDetailViewInterface=userDetailView;
    }
    @Override
    public void upLoadImage(){
        userInterface.upLoadImage();
    }


    @Override
    public void onUploadImageSuccess(){

    }

    @Override
    public void onUploadImageFailure(){

    }
}
