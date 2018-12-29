package com.example.l.EtherLet.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class UserBusiness implements UserInterface {
    @Override
    public void login(final OnLoginListener listener, final LocalCallBack callBack, Context context, Map<String, Object> map) {
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
    public void upLoadImage(final UserBusiness.DetailCallBack callBack, Context context, Bitmap bitmap, int user_id){
        VolleyRequest.uploadImage(context.getString(R.string.host_url_real_share) + context.getString(R.string.upload_user_image_path) + user_id, context, bitmap, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onUploadImageSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onUploadImageFailure();
            }
        });
    }

    @Override
    public void upLoadKey(final UserBusiness.DetailCallBack callBack,Context context,Map<String,Object> map,int user_id){
        /**
         * 通过map传递private key值
         * POST地址为upload_user_priavte_key_path_user_id
         */
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST,context.getString(R.string.host_url_real_share) + context.getString(R.string.upload_user_private_key_path) + user_id,map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onUploadKeySuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onUploadKeyFailure();
            }
        });
    }
    @Override
    public void registration(final OnRegistrationListener listener, final LocalCallBack callBack, Context context, Map<String, Object> map) {
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

    public interface DetailCallBack{
        void onUploadImageSuccess(JSONObject jsonObject);
        void onUploadImageFailure();
        void onUploadKeySuccess(JSONObject jsonObject);
        void onUploadKeyFailure();
    }

}
