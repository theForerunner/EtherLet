package com.example.l.EtherLet.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public interface UserDetailPresenterInterface {
    void upLoadImage(Context context, Bitmap bitmap, int user_id);

    void upLoadKey(Context context, Map<String,Object> map, int user_id);
}
