package com.example.l.EtherLet.network;

import android.util.Log;

import com.example.l.EtherLet.model.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
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

    public static BigDecimal parseJsonToAccountBalance(JSONObject jsonObject){
        BigDecimal balance=null;
        try{
            balance=new BigDecimal(jsonObject.getString("result"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return balance;
    }
}
