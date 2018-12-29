package com.example.l.EtherLet.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Map;

public interface UserInterface {
    void login(final OnLoginListener listener, final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
    void registration(final OnRegistrationListener listener, final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
    void upLoadImage(final UserBusiness.DetailCallBack callBack, Context context, Bitmap bitmap, int user_id);
    void upLoadKey(final UserBusiness.DetailCallBack callBack,Context context,Map<String, Object> map,int user_id);
}
