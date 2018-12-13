package com.example.l.EtherLet.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class PostList implements PostListInterface {

    @Override
    public void getPostListData(final LoadDataCallBack callBack, Context context) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.get_theme_list_path), null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                callBack.onSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onFailure();
            }
        });
    }

    @Override
    public void addNewPost(final LoadDataCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real) + context.getString(R.string.add_theme_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
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
