package com.example.l.EtherLet.model;

import android.content.Context;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class ThemeList implements ThemeListInterface {

    @Override
    public void getThemeListData(final LoadDataCallBack callBack, Context context) {
        VolleyRequest.getJSONObject(context.getString(R.string.host_url_real_share) + context.getString(R.string.get_theme_list_path), null, context, new VolleyCallback() {
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
    public void addNewTheme(final LoadDataCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(context.getString(R.string.host_url_real) + context.getString(R.string.add_theme_path), map, context, new VolleyCallback() {
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
