package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.List;

public interface CoinListInterface {
    void getInfoListData(final InfoLoadDataCallBack callBack, Context context);
    void getNameListData(final NameLoadDataCallBack callBack, Context context);
    void setNameList(List<String> list);
    void setInfoList(List<CoinInfo> list);
    List<CoinInfo> getInfoList();
    List<String> getNameList();
    void setInfoUrl();
    String getInfoUrl();
    List<CoinInfo> initDefaultInfoList();
    boolean isInit();
}