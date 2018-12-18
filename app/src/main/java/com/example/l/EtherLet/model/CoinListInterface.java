package com.example.l.EtherLet.model;

import android.content.Context;

import java.util.List;

public interface CoinListInterface {
    void getInfoListData(final CoinInfoList.InfoLoadDataCallBack callBack, Context context);
    boolean isInit();
}