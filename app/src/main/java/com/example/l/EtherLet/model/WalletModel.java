package com.example.l.EtherLet.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;

public class WalletModel {
    private String url ="https://ropsten.infura.io/v3/311d966c7f17491d9528f19b47dea261";
    private Web3j web3j;
    private Credentials credentials;

    public WalletModel(String privateKey){
        web3j=Web3j.build(new HttpService(url));
        credentials  = Credentials.create(privateKey);
    }

    public void getBalance(final balanceCallBack callBack, Context context){
        VolleyRequest.getJSONObject(
                JsonObjectRequest.Method.POST,
                "https://api-ropsten.etherscan.io/api?module=account&action=balance&address="+credentials.getAddress()+"&tag=latest&apikey=F3SHXIUD6T164DPYINVJ1HHYBA4WWDFMBT",
                null,
                context,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        callBack.onSuccess(jsonObject);
                    }

                    @Override
                    public void onFailure() {
                        callBack.onFailure();
                    }
                });
    }

    public void makeTransaction(String toAddress,float sum){
        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, toAddress,
                    BigDecimal.valueOf(sum), Convert.Unit.ETHER).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTransactionList(){
        String apiUrlL="http://api-ropsten.etherscan.io/api?module=account&action=txlist&address=";
        String apiUrlH="&startblock=0&endblock=99999999&sort=asc&apikey=";
        String token="F3SHXIUD6T164DPYINVJ1HHYBA4WWDFMBT";
        //TODO 异步网络请求
        return null;
    }

    public interface balanceCallBack{
        void onSuccess(JSONObject jsonObject);

        void onFailure();
    }
}
