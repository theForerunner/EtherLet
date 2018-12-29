package com.example.l.EtherLet.presenter;

import android.content.Context;

public interface InfoPresenterInterface {
    void loadInfoList(Context context);
    void getCandleEntryList(String symbol,String trader,String _enum,Context context);
}
