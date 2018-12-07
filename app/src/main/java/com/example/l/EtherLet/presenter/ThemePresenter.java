package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.ThemeList;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.ThemeViewInterface;

import org.json.JSONObject;

public class ThemePresenter implements ThemePresenterInterface, ThemeList.LoadDataCallBack {
    private final ThemeViewInterface themeViewInterface;
    private final ThemeList themeList;

    public ThemePresenter(ThemeViewInterface themeViewInterface) {
        this.themeViewInterface = themeViewInterface;
        this.themeList = new ThemeList();
    }

    @Override
    public void loadThemeList(Context context) {
        themeList.getThemeListData(this, context);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        themeViewInterface.showThemeList(JSONParser.parseJsonToThemeList(jsonObject));
    }

    @Override
    public void onFailure() {

    }
}
