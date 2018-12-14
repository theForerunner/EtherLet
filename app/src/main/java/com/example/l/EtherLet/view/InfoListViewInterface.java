package com.example.l.EtherLet.view;

import android.content.Context;

import com.example.l.EtherLet.model.CoinInfo;

import java.util.List;

public interface InfoListViewInterface {
    void initInfoList(List<CoinInfo> list);
    void updateInfoList(List<CoinInfo> list);
    void showFailMessage();
}
