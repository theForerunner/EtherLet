package com.example.l.EtherLet.model;

import android.content.Context;

public interface CandleEntryListInterface {
    void getCandleEntryListData(String symbol,String trader,String _enum,final CandleEntryList.CandleEntryListLoadDataCallBack callBack, final Context context);
}
