package com.example.l.EtherLet.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

public class CandleEntryList implements CandleEntryListInterface{

    private String url="https://data.block.cc/api/v1/kline";


    @Override
    public void getCandleEntryListData(String symbol,String trader,String _enum,final CandleEntryListLoadDataCallBack callBack, final Context context){

        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, url+makeUrl(symbol,trader,_enum), null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onCandleSuccess(jsonObject);

            }

            @Override
            public void onFailure() {
                callBack.onCandleFailure();
            }
        });

    }

    public interface CandleEntryListLoadDataCallBack {
        void onCandleSuccess(JSONObject jsonObject);
        void onCandleFailure();
    }
    public String makeUrl(String symbol,String trader,String _enum){
        String temp="?market="+trader+"&symbol_pair="+symbol+"_USD&type="+_enum;
        return temp;
    }
}
