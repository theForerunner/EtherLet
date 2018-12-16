package com.example.l.EtherLet.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.l.EtherLet.model.WalletModel;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.view.WalletFragment;

import org.json.JSONObject;

import java.math.RoundingMode;

public class WalletPresenter implements WalletModel.ApiAccessCallBack {
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
    public void getDollarBalance(Context context){
        myWallet.getDollarBalance(this,context);
    }

    public Bitmap sendMoney(){
        //TODO 新活动，显示好友列表，搜索用户和扫描二维码选项
        return myWallet.getAddressQrCode();
    }

    public void requestMoney(){
        //TODO 新活动，显示好友列表，搜索用户和展示二维码选项
    }

    public void getTransactionList(Context context){
        myWallet.getTransactionList(this,context);
    }

    @Override
    public void onGetBalanceSuccess(JSONObject ethObject){
        walletFragment.showBalance(JSONParser.parseJsonToAccountBalance(ethObject).setScale(2,RoundingMode.HALF_UP));
    }

    @Override
    public void onGetTxListSuccess(JSONObject jsonObject){
        walletFragment.showTransactionList(JSONParser.parseJsonToTxList(jsonObject));
    }

    @Override
    public void onFailure(){
        System.out.println("Connection Error: Volley Fail!!");
    }
}
