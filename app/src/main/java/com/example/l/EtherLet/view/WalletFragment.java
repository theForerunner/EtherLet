package com.example.l.EtherLet.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.Transaction;
import com.example.l.EtherLet.presenter.WalletPresenter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WalletFragment extends Fragment implements WalletInterface {

    private WalletPresenter walletPresenter;
    private RecyclerView transactionListRecyclerView;
    private TransactionAdapter transactionAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        transactionListRecyclerView=rootView.findViewById(R.id.tx_list_recycler);
        transactionListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionAdapter=new TransactionAdapter(initDefaultTransactionList());
        transactionListRecyclerView.setAdapter(transactionAdapter);

        walletPresenter.getBalance(this.getActivity());
        walletPresenter.getTransactionList(this.getActivity());
        return rootView;
    }

    @Override
    public void showBalance(BigDecimal balance) {
        TextView ethView = this.getActivity().findViewById(R.id.balanceEth);
        TextView dollarView = this.getActivity().findViewById(R.id.balanceDollar);
        ethView.setText(balance.toString() + " ETH");
        //dollarView.setText("$"+dollarBalance.toString()+" USD");
    }

    @Override
    public void showTransactionList(List<Transaction> transactionList) {
        transactionAdapter=new TransactionAdapter(transactionList);
        transactionListRecyclerView.setAdapter(transactionAdapter);
    }

    @Override
    public void sendMoney() {

    }

    @Override
    public void requestMoney() {
    }

    private class TransactionHolder extends RecyclerView.ViewHolder{
        private Transaction mTransaction;
        private TextView timeStampView;
        private TextView senderView;
        private TextView receiverView;
        private TextView valueView;
        private TextView statusView;

        private TransactionHolder(View itemView){
            super(itemView);
            timeStampView=itemView.findViewById(R.id.timestamp);
            senderView=itemView.findViewById(R.id.sender);
            receiverView=itemView.findViewById(R.id.receiver);
            valueView=itemView.findViewById(R.id.value);
            statusView=itemView.findViewById(R.id.status);
        }

        private void bindTx(Transaction tx){
            mTransaction=tx;
            timeStampView.setText("Time: "+mTransaction.getTimeStamp());
            senderView.setText("Sender: "+mTransaction.getSenderAddress());
            receiverView.setText("Receiver: "+mTransaction.getReceiverAddress());
            valueView.setText("Value: "+mTransaction.getValue());
            statusView.setText("Status: "+mTransaction.getValue());
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder>{
        private List<Transaction> transactionList;

        TransactionAdapter(List<Transaction> transactionList){
            this.transactionList=transactionList;
        }

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup viewGroup,int i){
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.transaction_item,viewGroup,false);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionHolder transactionHolder,int i){
            Transaction transaction=transactionList.get(i);
            transactionHolder.bindTx(transaction);
        }

        @Override
        public int getItemCount(){
            return transactionList.size();
        }
    }

    private List<Transaction> initDefaultTransactionList(){
        List<Transaction> defaultList=new ArrayList<>();
        Transaction transaction=new Transaction(true);
        for(int i=0;i<5;i++){
            defaultList.add(transaction);
        }
        return defaultList;
    }
}