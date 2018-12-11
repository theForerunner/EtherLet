package com.example.l.EtherLet.model;

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

public class WalletInit {
    private String url ="https://ropsten.infura.io/v3/311d966c7f17491d9528f19b47dea261";
    //private String privateKey="5f073440e41311395fcc0ff5b10454040ef332b02d2caf6976231450aede0f6a";
    private Web3j web3j;
    private Credentials credentials;

    public WalletInit(String privateKey){
        web3j=Web3j.build(new HttpService(url));
        credentials  = Credentials.create(privateKey);
    }

    public BigDecimal getBalance(){
        DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(13);
        EthGetBalance ethGetBalance = null;
        BigDecimal balance=null;
        try {
            ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            balance=Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public String makeTransaction(String toAddress,float sum){
        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, toAddress,
                    BigDecimal.valueOf(sum), Convert.Unit.ETHER).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionReceipt.getTransactionHash();
    }

    public String getTransactionList(){
        String apiUrlL="http://api-ropsten.etherscan.io/api?module=account&action=txlist&address=";
        String apiUrlH="&startblock=0&endblock=99999999&sort=asc&apikey=";
        String token="F3SHXIUD6T164DPYINVJ1HHYBA4WWDFMBT";
        //TODO 异步网络请求
        return null;
    }
}
