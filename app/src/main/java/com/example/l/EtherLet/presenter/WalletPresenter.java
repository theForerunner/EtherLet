package com.example.l.EtherLet.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.WalletModel;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;
import com.example.l.EtherLet.view.WalletFragment;
import com.example.l.EtherLet.view.WalletInterface;

import org.json.JSONObject;

import java.math.RoundingMode;

public class WalletPresenter implements WalletModel.ApiAccessCallBack {
    private WalletModel myWallet;
    private WalletInterface walletInterface;
    private String privateKey;
    public boolean isError;

    public WalletPresenter(WalletFragment walletFragment,Context context){
        privateKey=null;
        isError=false;
        getUserPrivateKey(context);
        if(privateKey==null){
            isError=true;
            return;
        }
        myWallet=new WalletModel(privateKey);
        this.walletInterface =walletFragment;
    }

    private void getUserPrivateKey(Context context){
        //String privateKey="5f073440e41311395fcc0ff5b10454040ef332b02d2caf6976231450aede0f6a";

        String privateKey;
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, context.getString(R.string.host_url_real_share) + context.getString(R.string.get_user_path), null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                onGetPrivateKeySuccess(jsonObject);
            }

            @Override
            public void onFailure(){
                System.out.println("Connection Error: Volley Fail!!");
            }
        });
    }

    public void getBalance(Context context){
        myWallet.getBalance(this,context);
    }
    public void getDollarBalance(Context context){
        myWallet.getDollarBalance(this,context);
    }

    public Bitmap requestMoney(){
        //TODO 新活动，显示好友列表，搜索用户
        return myWallet.getAddressQrCode();
    }

    public void sendMoney(String address,float sum){
        //TODO 新活动，显示好友列表，搜索用户
        myWallet.makeTransaction(address,sum);
    }



    /*
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("DT",val);
        }
    };
    */

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

    public void onGetPrivateKeySuccess(JSONObject jsonObject){
        privateKey=JSONParser.parseJsonToUser(jsonObject).getUserKey();
    }

    @Override
    public void onFailure(){
        System.out.println("Connection Error: Volley Fail!!");
    }
}
