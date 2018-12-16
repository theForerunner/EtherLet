package com.example.l.EtherLet.network;

import android.content.Context;

import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject jsonObject, Context context);

    void onFailure();
}

