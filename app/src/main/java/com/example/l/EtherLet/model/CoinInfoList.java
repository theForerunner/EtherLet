package com.example.l.EtherLet.model;



import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.network.HttpUtil;
import com.example.l.EtherLet.network.InfoJSONParser;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class CoinInfoList implements CoinListInterface {

    private List<CoinInfo> infoList=new ArrayList<>();
    private List<String> nameList=new ArrayList<>();
    private String url="https://data.block.cc/api/v1/price?symbol_name=";
    private boolean isInitUrl=false;
    private boolean isInitList=false;


    @Override
    public void getInfoListData(final InfoLoadDataCallBack callBack, Context context){
        if(isInitUrl){
            VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, url, null, context, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject jsonObject) {callBack.onInfoSuccess(jsonObject);isInitList=true;}
                @Override
                public void onFailure() {callBack.onInfoFailure(); }
            });
        }


    }

        /*HttpUtil.sendRequestWithOkhttp(getInfoUrl(),new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                infoList=infoJSONParser.parseJSONInfoList(response);
            }
        });
        return infoList;*/


    @Override
    public void getNameListData(final NameLoadDataCallBack callBack, Context context){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, "https://data.block.cc/api/v1/symbols", null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {callBack.onNameSuccess(jsonObject);}
            @Override
            public void onFailure() { callBack.onNameFailure(); }
        });


        /*HttpUtil.sendRequestWithOkhttp("https://data.block.cc/api/v1/symbols",new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                nameList=InfoJSONParser.parseJSONNameList(response);
            }
        });*/

    }

    @Override
    public void setInfoUrl(){
        for(int i=0;i<nameList.size();i++){
            if(i==0){
               url=url+nameList.get(0);
            }
            url=url+","+nameList.get(i);
        }
        isInitUrl=true;
    }

    @Override
    public String getInfoUrl(){
        return url;
    }
    @Override
    public void setNameList(List<String> list){
        this.nameList=list;
    }

    @Override
    public void setInfoList(List<CoinInfo> list){
        this.infoList=list;
    }

    @Override
    public  List<CoinInfo> getInfoList(){
        return infoList;
    }
    @Override
    public  List<String> getNameList(){
        return nameList;
    }

    @Override
    public List<CoinInfo> initDefaultInfoList() {
        List<CoinInfo> defaultInfoList = new ArrayList<>();
        CoinInfo info = new CoinInfo();
        info.setSymbol("BTC");
        info.setName("bitcoin");
        info.setPriceUSD("8134.85601071");
        info.setHigh("8368.41733741");
        info.setLow("7972.64470958");
        info.setChangeMonthly("0.008");
        for (int i = 0; i < 100; i++) {
            defaultInfoList.add(info);
        }
        return defaultInfoList;
    }


    @Override
    public boolean isInit(){
        return isInitList;
    }


}
