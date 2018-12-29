package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.CandleEntryList;
import com.example.l.EtherLet.model.CandleEntryListInterface;
import com.example.l.EtherLet.model.CoinInfoList;
import com.example.l.EtherLet.model.CoinListInterface;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.InfoListViewInterface;
import com.fasterxml.jackson.core.JsonParser;

import org.json.JSONObject;

public class InfoPresenter implements InfoPresenterInterface, CoinInfoList.InfoLoadDataCallBack,CandleEntryList.CandleEntryListLoadDataCallBack {
    private final InfoListViewInterface infoListViewInterface;
    private final CoinListInterface infoList;
    private final CandleEntryListInterface candleEntryList;

    public InfoPresenter(InfoListViewInterface infoListViewInterface){
        this.infoList=new CoinInfoList();
        this.infoListViewInterface=infoListViewInterface;
        this.candleEntryList=new CandleEntryList();
    }

    @Override
    public void loadInfoList(Context context){
        infoList.getInfoListData(this,context);
    }

    @Override
    public void getCandleEntryList(String symbol,String trader,String _enum,Context context){
        candleEntryList.getCandleEntryListData(symbol,trader,_enum,this,context);
    }

    @Override
    public void onInfoSuccess(JSONObject jsonObject){
        if(infoList.isInit()){
            infoListViewInterface.updateInfoList(JSONParser.parseJSONToInfoList(jsonObject));
        }
        infoListViewInterface.initInfoList(JSONParser.parseJSONToInfoList(jsonObject));
    }

    @Override
    public void onInfoFailure(){

    }
    @Override
    public void onCandleSuccess(JSONObject jsonObject){
        infoListViewInterface.setCandleEntryList(JSONParser.parseJSONToCandleEntryList(jsonObject));
    }
    @Override
    public void onCandleFailure(){}
}
