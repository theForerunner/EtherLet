package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.CoinInfo;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.List;

public interface InfoListViewInterface {
    void initInfoList(List<CoinInfo> list);
    void updateInfoList(List<CoinInfo> list);
    void setCandleEntryList(List<CandleEntry> list);
    void showFailMessage();
}
