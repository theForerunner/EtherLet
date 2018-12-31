package com.example.l.EtherLet.model;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class FriendList implements FriendListInterface {
    @Override
    public void loadFriendListData(final LoadDataCallBack callBack, Context context, int user_id) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, context.getString(R.string.host_url_real_share) + context.getString(R.string.get_friend_list_path) + user_id, null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onLoadListSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onFailure();
            }
        });
    }

    @Override
    public void deleteFriend(final LoadDataCallBack callback,Context context,int friendship_id){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.delete_friend_path) + friendship_id, null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDeleteSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callback.onFailure();
            }
        });
    }

    @Override
    public void addFriend(final LoadDataCallBack callBack,Context context,Map<String,Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.add_friend_path),map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onAddSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onFailure();
            }
        });
    }

    public interface LoadDataCallBack {
        void onLoadListSuccess(JSONObject jsonObject);
        void onDeleteSuccess(JSONObject jsonObject);
        void onAddSuccess(JSONObject jsonObject);

        void onFailure();
    }
}
