package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

public interface UserInterface {
    void login(final OnLoginListener listener, final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
    void registration(OnRegistrationListener listener, final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
}
