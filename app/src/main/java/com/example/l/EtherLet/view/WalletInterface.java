package com.example.l.EtherLet.view;

import com.example.l.EtherLet.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletInterface {
    public void showBalance(BigDecimal balance);
    public void showTransactionList(List<Transaction> transactionList);
    public void sendMoney();
    public void requestMoney();
}
