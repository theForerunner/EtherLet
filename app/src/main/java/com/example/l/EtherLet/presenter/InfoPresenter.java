package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.model.CoinInfoList;
import com.example.l.EtherLet.model.CoinListInterface;
import com.example.l.EtherLet.model.InfoLoadDataCallBack;
import com.example.l.EtherLet.model.NameLoadDataCallBack;
import com.example.l.EtherLet.network.InfoJSONParser;
import com.example.l.EtherLet.view.InfoListViewInterface;

import org.json.JSONObject;

import java.util.List;

public class InfoPresenter implements InfoPresenterInterface,InfoLoadDataCallBack,NameLoadDataCallBack {
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
    public void loadNameList(Context context){
        infoList.getNameListData(this,context);
    }

    @Override
    public void loadDefaultData(){
        infoListViewInterface.initInfoList(infoList.initDefaultInfoList());
    }

    @Override
    public void onInfoSuccess(JSONObject jsonObject){
        infoList.setInfoList(InfoJSONParser.parseJSONInfoList(jsonObject));
        if(infoList.isInit()){
            infoListViewInterface.upDateInfoList(infoList.getInfoList());
        }
        infoListViewInterface.initInfoList(infoList.getInfoList());
    }

    @Override
    public void onInfoFailure(){

    }

    @Override
    public void onNameSuccess(JSONObject jsonObject){
        infoList.setNameList(InfoJSONParser.parseJSONNameList(jsonObject));
        infoList.setInfoUrl();
        infoList.getInfoListData(this,infoListViewInterface.getInfoContext());
    }
    @Override
    public void onNameFailure(){

    }


}
