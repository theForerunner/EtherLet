package com.example.l.EtherLet.model;


import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

public class FriendList implements FriendListInterface {
    @Override
    public void loadFriendListData(final LoadDataCallBack callBack, Context context, int user_id) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, context.getString(R.string.host_url_real_share) + context.getString(R.string.get_friend_list_path) + user_id, null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onFailure();
            }
        });
    }

    public interface LoadDataCallBack {
        void onSuccess(JSONObject jsonObject);

        void onFailure();
    }
}
