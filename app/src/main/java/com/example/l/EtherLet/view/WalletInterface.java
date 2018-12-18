package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.WalletModel;

import java.math.BigDecimal;
import java.util.List;

public interface WalletInterface {
    public void showBalance(BigDecimal balance);

    public void showTransactionList(List<WalletModel.Transaction> transactionList);

    public void requestMoney();

    public void sendMoney();
}