package com.example.l.EtherLet.model;

import org.json.JSONObject;

public interface InfoLoadDataCallBack {
    void onInfoSuccess(JSONObject jsonObject);
    void onInfoFailure();
}
