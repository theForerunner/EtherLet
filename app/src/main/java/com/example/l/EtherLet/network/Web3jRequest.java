package com.example.l.EtherLet.network;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class Web3jRequest implements Runnable {
    Web3j web3j;
    Credentials credentials;
    String toAddress;
    float sum;
    Handler handler;

    public Web3jRequest(Web3j _web3j, Credentials _credentials, String _toAddress, float _sum,final Handler _handler){
        web3j=_web3j;
        credentials=_credentials;
        toAddress=_toAddress;
        sum=_sum;
        handler=_handler;
    }
    @Override
    public void run(){
        TransactionReceipt transactionReceipt=null;
        try {
            transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, toAddress,
                    BigDecimal.valueOf(sum), Convert.Unit.ETHER).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message msg=new Message();
        Bundle data=new Bundle();
        data.putString("value",transactionReceipt.getTransactionHash());
        msg.setData(data);
        handler.sendMessage(msg);
    }
}
