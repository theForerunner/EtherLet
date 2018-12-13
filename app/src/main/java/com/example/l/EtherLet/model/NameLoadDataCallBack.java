package com.example.l.EtherLet.model;

import org.json.JSONObject;

public interface NameLoadDataCallBack {
    void onNameSuccess(JSONObject jsonObject);
    void onNameFailure();
}
