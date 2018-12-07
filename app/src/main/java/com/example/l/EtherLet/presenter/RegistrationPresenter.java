package com.example.l.EtherLet.presenter;

import com.example.l.EtherLet.model.OnRegistrationListener;
import com.example.l.EtherLet.model.UserBusiness;
import com.example.l.EtherLet.model.UserInterface;
import com.example.l.EtherLet.view.RegistrationViewInterface;

public class RegistrationPresenter {
    private UserInterface userBusiness;
    private RegistrationViewInterface registrationView;
    public RegistrationPresenter(RegistrationViewInterface registrationView){
        this.registrationView=registrationView;
        this.userBusiness=new UserBusiness();
    }
    public void registration(){

        userBusiness.registration(registrationView.getId(), registrationView.getPassword(), new OnRegistrationListener() {
            @Override
            public void registrationSuccess() {
                registrationView.registrationSuccess();
            }

            @Override
            public void registrationFail() {

                registrationView.registrationFail();
            }
        });
    }
}
