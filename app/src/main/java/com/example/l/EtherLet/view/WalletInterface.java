package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.WalletModel;

import java.math.BigDecimal;
import java.util.List;

public interface WalletInterface {
    void showBalance(BigDecimal balance);

    void showTransactionList(List<WalletModel.Transaction> transactionList);

    void requestMoney();

    void sendMoney();
}