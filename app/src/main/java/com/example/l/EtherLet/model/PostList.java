package com.example.l.EtherLet.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class PostList implements PostListInterface {

    /**
     * 加载主题帖列表
     * @param callBack
     * @param context
     */
    @Override
    public void loadPostListData(final LoadDataCallBack callBack, Context context) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, context.getString(R.string.host_url_real_share) + context.getString(R.string.get_post_list_path), null, context, new VolleyCallback() {
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

    /**
     * 发表一个主题帖
     * @param callBack
     * @param context
     * @param map
     */
    @Override
    public void addNewPost(final LoadDataCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.add_post_path), map, context, new VolleyCallback() {
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

    /**
     * 回调
     */
    public interface LoadDataCallBack {
        void onSuccess(JSONObject jsonObject);

        void onFailure();
    }
}
