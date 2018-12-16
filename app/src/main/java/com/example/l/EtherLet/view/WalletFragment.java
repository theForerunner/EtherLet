package com.example.l.EtherLet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.WalletModel;
import com.example.l.EtherLet.presenter.WalletPresenter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;


public class WalletFragment extends Fragment {

    private WalletPresenter walletPresenter;
    private RecyclerView transactionListRecyclerView;
    private TransactionAdapter transactionAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomSheetDialog newQRCodeBottomSheetDialog;
    private Button sendMoney;

    public static WalletFragment newInstance() {
        WalletFragment f = new WalletFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        walletPresenter = new WalletPresenter(this);
        transactionListRecyclerView=rootView.findViewById(R.id.tx_list_recycler);
        transactionListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionAdapter=new TransactionAdapter(initDefaultTransactionList());
        transactionListRecyclerView.setAdapter(transactionAdapter);

        walletPresenter.getBalance(this.getActivity());
        walletPresenter.getTransactionList(this.getActivity());

        setUpBottomSheetDialog();

        sendMoney=rootView.findViewById(R.id.Send);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMoney();
            }
        });

        return rootView;
    }

    public void showBalance(BigDecimal balance) {
        TextView ethView = this.getActivity().findViewById(R.id.balanceEth);
        TextView dollarView = this.getActivity().findViewById(R.id.balanceDollar);
        ethView.setText(balance.toString() + " ETH");
        //dollarView.setText("$"+dollarBalance.toString()+" USD");
    }

    public void showTransactionList(List<WalletModel.Transaction> transactionList) {
        transactionAdapter=new TransactionAdapter(transactionList);
        transactionListRecyclerView.setAdapter(transactionAdapter);
    }

    public void sendMoney() {
        newQRCodeBottomSheetDialog.show();
    }

    public void requestMoney() {
    }


    /**
     * Recycler view for transaction list
     */
    private class TransactionHolder extends RecyclerView.ViewHolder{
        private WalletModel.Transaction mTransaction;
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

        private void bindTx(WalletModel.Transaction tx){
            mTransaction=tx;
            timeStampView.setText("Time: "+mTransaction.getTimeStamp());
            senderView.setText("Sender: "+mTransaction.getSenderAddress());
            receiverView.setText("Receiver: "+mTransaction.getReceiverAddress());
            valueView.setText("Value: "+mTransaction.getValue());
            statusView.setText("Status: "+mTransaction.getValue());
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder>{
        private List<WalletModel.Transaction> transactionList;

        TransactionAdapter(List<WalletModel.Transaction> transactionList){
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
            WalletModel.Transaction transaction=transactionList.get(i);
            transactionHolder.bindTx(transaction);
        }

        @Override
        public int getItemCount(){
            return transactionList.size();
        }
    }

    private List<WalletModel.Transaction> initDefaultTransactionList(){
        List<WalletModel.Transaction> defaultList=new ArrayList<>();
        WalletModel.Transaction transaction=new WalletModel.Transaction(true);
        for(int i=0;i<5;i++){
            defaultList.add(transaction);
        }
        return defaultList;
    }


    /**
     * BottomSheet for QR code
     */
    private void setUpBottomSheetDialog() {
        newQRCodeBottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.qr_code_layout, null);
        newQRCodeBottomSheetDialog.setContentView(bottomSheetView);
        newQRCodeBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        newQRCodeBottomSheetDialog.setCancelable(true);
        newQRCodeBottomSheetDialog.setCanceledOnTouchOutside(true);

        ImageView qrImage=bottomSheetView.findViewById(R.id.qr_code);
        qrImage.setImageBitmap(walletPresenter.sendMoney());
    }
}