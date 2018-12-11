package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.Map;

public interface ThemeListInterface {
    void getThemeListData(ThemeList.LoadDataCallBack callBack, Context context);

    void addNewTheme(ThemeList.LoadDataCallBack callBack, Context context, Map<String, Object> map);

}
