package com.example.l.EtherLet.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.l.EtherLet.model.WalletModel;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.WalletFragment;
import com.example.l.EtherLet.view.WalletInterface;

import org.json.JSONObject;

import java.math.RoundingMode;

public class WalletPresenter implements WalletModel.ApiAccessCallBack {
    private WalletModel myWallet;
    private WalletInterface walletInterface;

    public WalletPresenter(WalletFragment walletFragment){
        myWallet=new WalletModel(getUserPrivateKey());
        this.walletInterface =walletFragment;
    }

    private String getUserPrivateKey(){
        String privateKey="5f073440e41311395fcc0ff5b10454040ef332b02d2caf6976231450aede0f6a";
        return privateKey;
    }

    public void getBalance(Context context){
        myWallet.getBalance(this,context);
    }
    public void getDollarBalance(Context context){
        myWallet.getDollarBalance(this,context);
    }

    public Bitmap requestMoney(){
        //TODO 新活动，显示好友列表，搜索用户和扫描二维码选项
        return myWallet.getAddressQrCode();
    }

    public void sendMoney(String address){
        //TODO 新活动，显示好友列表，搜索用户和展示二维码选项；做金额输入页面
        myWallet.makeTransaction(address,(float)0.01,handler);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
        }
    };

    public void getTransactionList(Context context){
        myWallet.getTransactionList(this,context);
    }

    @Override
    public void onGetBalanceSuccess(JSONObject ethObject){
        walletInterface.showBalance(JSONParser.parseJsonToAccountBalance(ethObject).setScale(2,RoundingMode.HALF_UP));
    }

    @Override
    public void onGetTxListSuccess(JSONObject jsonObject){
        walletInterface.showTransactionList(JSONParser.parseJsonToTxList(jsonObject));
    }

    @Override
    public void onFailure(){
        System.out.println("Connection Error: Volley Fail!!");
    }
}
