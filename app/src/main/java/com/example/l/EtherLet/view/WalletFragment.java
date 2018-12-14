package com.example.l.EtherLet.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.presenter.WalletPresenter;
import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WalletFragment extends Fragment implements WalletInterface {

    private WalletPresenter walletPresenter;

    public static WalletFragment newInstance() {
        WalletFragment f = new WalletFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        walletPresenter = new WalletPresenter(this);
        walletPresenter.getBalance(this.getActivity());
        return rootView;
    }

    @Override
    public void showBalance(BigDecimal balance) {
        TextView ethView = this.getActivity().findViewById(R.id.balanceEth);
        TextView dollarView = this.getActivity().findViewById(R.id.balanceDollar);
        ethView.setText(balance.toString() + " ETH");
        //todo 实时查询美元汇率
    }

    @Override
    public void showTransactionList() {

    }

    @Override
    public void sendMoney() {

    }

    @Override
    public void requestMoney() {
    }
}