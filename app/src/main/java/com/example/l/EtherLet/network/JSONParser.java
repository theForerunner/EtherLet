package com.example.l.EtherLet.network;

import android.util.Log;

import com.example.l.EtherLet.model.Theme;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public static List<Theme> parseJsonToThemeList(JSONObject jsonObject) {
        List<Theme> themeList = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray themeArray = data.getJSONArray("list");
            for (int i = 0; i < themeArray.length(); i++) {
                JSONObject themeObject = themeArray.getJSONObject(i);
                Log.i("DT", themeObject.toString());
                Theme theme = new Theme(themeObject.getInt("id"), themeObject.getString("subtitle"), themeObject.getInt("creatorId"), themeObject.getString("creatorName"), new Timestamp(themeObject.getLong("createDate")), new Timestamp(themeObject.getLong("replyDate")));
                themeList.add(theme);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("DT", "Length of themeList: " + themeList.size());
        return themeList;

    }
}
