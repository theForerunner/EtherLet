package com.example.l.EtherLet.model;



import com.example.l.EtherLet.network.HttpUtil;
import com.example.l.EtherLet.network.InfoJSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoinInfoList implements CoinListInterface {

    private List<CoinInfo> infoList=new ArrayList<>();
    private List<String> nameList=new ArrayList<>();
    private InfoJSONParser infoJSONParser=new InfoJSONParser();


    @Override
    public List<CoinInfo> getInfoList(){
        HttpUtil.sendRequestWithOkhttp(getInfoUrl(),new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                infoList=infoJSONParser.parseJSONInfoList(response);
            }
        });

        //infoList=initDefaultInfoList();//测试
        return infoList;

    }
    @Override
    public void getNameList(){

        HttpUtil.sendRequestWithOkhttp("https://data.block.cc/api/v1/symbols",new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                nameList=infoJSONParser.parseJSONSymbolList(response);
            }
        });

    }

    @Override
    public String getInfoUrl(){
        String url="https://data.block.cc/api/v1/price?symbol_name=";
        for(int i=0;i<nameList.size()&&i<100;i++){
            if(i==0){
               url=url+nameList.get(0);
            }
            url=url+","+nameList.get(i);
        }
        return url;

    }

    @Override
    public void setNameList(List<String> list){
        this.nameList=list;
    }

    @Override
    public void setInfoList(List<CoinInfo> list){
        this.infoList=list;
    }

    private List<CoinInfo> initDefaultInfoList() {
        List<CoinInfo> defaultInfoList = new ArrayList<>();
        CoinInfo info = new CoinInfo();
        info.setSymbol("BTC");
        info.setName("bitcoin");
        info.setPriceUSD("8134.85601071");
        info.setHigh("8368.41733741");
        info.setLow("7972.64470958");
        info.setChangeMonthly("0.008");
        for (int i = 0; i < 20; i++) {
            defaultInfoList.add(info);
        }
        return defaultInfoList;
    }
}
