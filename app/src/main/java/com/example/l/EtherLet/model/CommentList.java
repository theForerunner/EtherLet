package com.example.l.EtherLet.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class CommentList implements CommentListInterface {

    /**
     * 加载评论列表
     * @param callBack
     * @param context
     * @param post_id
     */
    @Override
    public void loadCommentList(LoadDataCallBack callBack, Context context, int post_id) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, context.getString(R.string.host_url_real_share) + context.getString(R.string.get_comment_list_path) + post_id, null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onLoadCommentListSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onLoadCommentListFailure();
            }
        });
    }

    /**
     * 添加一条评论
     * @param callBack
     * @param context
     * @param map
     */
    @Override
    public void addNewComment(LoadDataCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.host_url_real_share) + context.getString(R.string.add_comment_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onAddNewCommentSuccess(jsonObject);
            }

            @Override
            public void onFailure() {
                callBack.onAddNewCommentFailure();
            }
        });
    }

    /**
     * 回调
     */
    public interface LoadDataCallBack {
        void onLoadCommentListSuccess(JSONObject jsonObject);

        void onLoadCommentListFailure();

        void onAddNewCommentSuccess(JSONObject jsonObject);

        void onAddNewCommentFailure();
    }
}
