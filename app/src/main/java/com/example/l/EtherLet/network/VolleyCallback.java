package com.example.l.EtherLet.network;

import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject jsonObject);

    void onFailure();
}

