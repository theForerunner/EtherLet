package com.example.l.EtherLet.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;
import com.example.l.EtherLet.network.Web3jRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class WalletModel {
    private String url ="https://ropsten.infura.io/v3/311d966c7f17491d9528f19b47dea261";
    private Web3j web3j;
    private Credentials credentials;

    public WalletModel(String privateKey){
        web3j=Web3j.build(new HttpService(url));
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

    public void makeTransaction(String toAddress,float sum,Handler handler){
        /*
        //TransactionReceipt transactionReceipt = null;
        try {
            //transactionReceipt =
            Transfer.sendFunds(
                    web3j, credentials, toAddress,
                    BigDecimal.valueOf(sum), Convert.Unit.ETHER).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        new Thread(new Web3jRequest(web3j, credentials, toAddress, sum,handler)).start();
    }

    public Bitmap getAddressQrCode(){
        Bitmap bitmap = null;
        BitMatrix result = null;
        String str=credentials.getAddress();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 480, 480);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
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

    public interface ApiAccessCallBack {
        void onGetBalanceSuccess(JSONObject jsonObject);
        void onGetTxListSuccess(JSONObject jsonObject);
        void onFailure();
    }
}
