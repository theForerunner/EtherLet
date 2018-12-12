package com.example.l.EtherLet.model;

import java.util.List;

public interface CoinListInterface {
    List<CoinInfo> getInfoList();
    void getNameList();
    void setNameList(List<String> list);
    void setInfoList(List<CoinInfo> list);
    String getInfoUrl();
}