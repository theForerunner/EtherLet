package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.CoinInfo;

import java.util.List;

public interface InfoListViewInterface {
    void showInfoList(List<CoinInfo> list);
    void showFailMessgae();
}
