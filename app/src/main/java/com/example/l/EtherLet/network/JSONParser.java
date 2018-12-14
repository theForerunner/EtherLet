package com.example.l.EtherLet.network;

import android.util.Log;

import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    private static int length=150;

    public static List<Post> parseJsonToPostList(JSONObject jsonObject) {
        List<Post> postList = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray postArray = data.getJSONArray("list");
            for (int i = 0; i < postArray.length(); i++) {
                JSONObject postObject = postArray.getJSONObject(i);
                Log.i("DT", postObject.toString());
                Post post = new Post(postObject.getInt("id"), postObject.getString("subtitle"), postObject.getInt("creatorId"), postObject.getString("creatorName"), new Timestamp(postObject.getLong("createDate")), 0);
                postList.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("DT", "Length of postList: " + postList.size());
        return postList;

    }

    public static List<String> parseJSONToNameList(JSONObject jsonObject){
        List<String> list=new ArrayList<>();
        try{
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

    public static List<CoinInfo> parseJSONToInfoList(JSONObject jsonObject){
        List<CoinInfo> list=new ArrayList<>();
        try{

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
