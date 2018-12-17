package com.example.l.EtherLet.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;
import com.example.l.EtherLet.presenter.QRCodeUtil;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

import static org.web3j.protocol.Web3j.*;

public class WalletModel {
    private String url ="https://ropsten.infura.io/v3/311d966c7f17491d9528f19b47dea261";
    private Web3j web3j;
    private Credentials credentials;

    public WalletModel(String privateKey){
        web3j=build(new HttpService(url));
        credentials  = Credentials.create(privateKey);
    }

    public void getBalance(final ApiAccessCallBack callBack, Context context){
        VolleyRequest.getJSONObject(
                JsonObjectRequest.Method.POST,
                "https://api-ropsten.etherscan.io/api?module=account&action=balance&address="+credentials.getAddress()+"&tag=latest&apikey=F3SHXIUD6T164DPYINVJ1HHYBA4WWDFMBT",
                null,
                context,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject,Context context) {
                        callBack.onGetBalanceSuccess(jsonObject);
                    }

                    @Override
                    public void onFailure() {
                        callBack.onFailure();
                    }
                });
    }

    public void getDollarBalance(final ApiAccessCallBack callback, Context context){
        VolleyRequest.getJSONObject(
                JsonObjectRequest.Method.POST,
                "https://api.etherscan.io/api?module=stats&action=ethprice&apikey=F3SHXIUD6T164DPYINVJ1HHYBA4WWDFMBT",
                null,
                context,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject, Context context) {
                        callback.onGetBalanceSuccess(jsonObject);
                    }

                    @Override
                    public void onFailure() {
                        callback.onFailure();
                    }
                }
        );
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

    public Bitmap getAddressQrCode(){
        return QRCodeUtil.createQRCodeBitmap(credentials.getAddress(),480,480);
    }

    public void getTransactionList(final ApiAccessCallBack callback,Context context){
        VolleyRequest.getJSONObject(
                JsonObjectRequest.Method.POST,
                "http://api-ropsten.etherscan.io/api?module=account&action=txlist&address="+credentials.getAddress()+"&startblock=0&endblock=99999999&sort=asc&apikey=F3SHXIUD6T164DPYINVJ1HHYBA4WWDFMBT",
                null,
                context,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject, Context context) {
                        callback.onGetTxListSuccess(jsonObject);
                    }

                    @Override
                    public void onFailure() {
                        callback.onFailure();
                    }
                }
        );
    }

    public interface ApiAccessCallBack {
        void onGetBalanceSuccess(JSONObject jsonObject);
        void onGetTxListSuccess(JSONObject jsonObject);
        void onFailure();
    }


    public static class Transaction {
        private String senderAddress;
        private String receiverAddress;
        private String value;
        private String timeStamp;
        private String status;

        public Transaction(){}

        public Transaction(boolean init){
            if(!init) return;
            senderAddress="jhdsbcscldjncsldnl327e8fsdnsd";
            receiverAddress="sdcskjcnlkjdnv3872sjdfvkjsd";
            value="16.00";
            timeStamp="20181212";
            status="1";
        }

        public String getSenderAddress() {
            return senderAddress;
        }

        public void setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public String getValue() {
            return value;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public String getStatus() {
            return status;
        }
    }


}
