package com.example.l.EtherLet.presenter;

import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.model.CoinInfoList;
import com.example.l.EtherLet.model.CoinListInterface;
import com.example.l.EtherLet.view.InfoListViewInterface;

import java.util.List;

public class InfoPresenter implements InfoPresenterInterface {
    private final InfoListViewInterface infoListViewInterface;
    private final CoinListInterface infoList;

    public InfoPresenter(InfoListViewInterface infoListViewInterface){
        this.infoList=new CoinInfoList();
        this.infoListViewInterface=infoListViewInterface;
    }

    @Override
    public void loadInfoList(){
        infoList.getNameList();
        List<CoinInfo> list=infoList.getInfoList();
        infoListViewInterface.showInfoList(list);
    }
}
