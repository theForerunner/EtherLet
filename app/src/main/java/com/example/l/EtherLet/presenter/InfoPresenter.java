package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.CoinInfoList;
import com.example.l.EtherLet.model.CoinListInterface;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.InfoListViewInterface;

import org.json.JSONObject;

public class InfoPresenter implements InfoPresenterInterface, CoinInfoList.InfoLoadDataCallBack{
    private final InfoListViewInterface infoListViewInterface;
    private final CoinListInterface infoList;

    public InfoPresenter(InfoListViewInterface infoListViewInterface){
        this.infoList=new CoinInfoList();
        this.infoListViewInterface=infoListViewInterface;
    }

    @Override
    public void loadInfoList(Context context){
        infoList.getInfoListData(this,context);
    }

    @Override
    public void onInfoSuccess(JSONObject jsonObject){
        //infoList.setInfoList(JSONParser.parseJSONToInfoList(jsonObject));
        //infoListViewInterface.updateInfoList(JSONParser.parseJSONToInfoList(jsonObject));
        infoListViewInterface.initInfoList(JSONParser.parseJSONToInfoList(jsonObject));
    }

    @Override
    public void onInfoFailure(){

    }
}
