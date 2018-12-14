package com.example.l.EtherLet.presenter;

import android.content.Context;

import com.example.l.EtherLet.model.WalletModel;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.WalletFragment;

import org.json.JSONObject;

import java.math.BigDecimal;

public class WalletPresenter implements WalletModel.balanceCallBack {
    private WalletModel myWallet;
    private WalletFragment walletFragment;

    public WalletPresenter(WalletFragment walletFragment){
        myWallet=new WalletModel(getUserPrivatekey());
        this.walletFragment=walletFragment;
    }

    private String getUserPrivatekey(){
        String privateKey="5f073440e41311395fcc0ff5b10454040ef332b02d2caf6976231450aede0f6a";
        return privateKey;
    }

    public void getBalance(Context context){
        myWallet.getBalance(this,context);
    }

    public void sendMoney(){
        //TODO 新活动，显示好友列表，搜索用户和扫描二维码选项
    }

    public void requestMoney(){
        //TODO 新活动，显示好友列表，搜索用户和展示二维码选项
    }

    @Override
    public void onSuccess(JSONObject jsonObject){
        walletFragment.showBalance(JSONParser.parseJsonToAccountBalance(jsonObject));
    }

    @Override
    public void onFailure(){
        //todo 失败信息
    }
}
