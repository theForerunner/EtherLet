package com.example.l.EtherLet.view;

import java.math.BigDecimal;

public interface WalletInterface {
    public void showBalance(BigDecimal balance);
    public void showTransactionList();
    public void sendMoney();
    public void requestMoney();
}
