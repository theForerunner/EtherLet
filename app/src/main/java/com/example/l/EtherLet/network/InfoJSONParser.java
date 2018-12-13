package com.example.l.EtherLet.network;

import com.android.volley.Response;
import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.model.CoinInfoList;
import com.example.l.EtherLet.model.CoinListInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class InfoJSONParser {


    public List<String> parseJSONSymbolList(okhttp3.Response response) throws IOException {

        List<String> list=new ArrayList<>();
        String responseData=response.body().string();
        try{
            JSONObject jsonObject=new JSONObject(responseData);
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int i=0;i<100;i++){
                JSONObject dataObject=dataList.getJSONObject(i);
                String name=dataObject.getString("name");
                list.add(name);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public List<CoinInfo> parseJSONInfoList(okhttp3.Response response) throws IOException{
        List<CoinInfo> list=new ArrayList<>();
        String responseData=response.body().string();
        try{
            JSONObject jsonObject=new JSONObject(responseData);
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<100;j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                CoinInfo info=new CoinInfo();
                info.setSymbol(dataObject.getString("symbol"));
                info.setName(dataObject.getString("name"));
                info.setPriceUSD(dataObject.getString("price"));
                info.setHigh(dataObject.getString("high"));
                info.setLow(dataObject.getString("low"));
                info.setVolume(dataObject.getString("volume"));
                info.setChangeHour(dataObject.getString("change_hourly"));
                info.setChangeDaily(dataObject.getString("change_daily"));
                info.setChangeWeekly(dataObject.getString("change_weekly"));
                info.setChangeMonthly(dataObject.getString("change_monthly"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }


}










