package com.example.l.EtherLet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.WalletModel;
import com.example.l.EtherLet.presenter.WalletPresenter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class WalletFragment extends Fragment implements WalletInterface{

    private WalletPresenter walletPresenter;
    private RecyclerView transactionListRecyclerView;
    private TransactionAdapter transactionAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomSheetDialog newQRCodeBottomSheetDialog;
    private BottomSheetDialog sendMoneyBottomSheet;
    private Button sendMoney;
    private Button requestMoney;
    private String toAddress;
    private TextView ethView;
    private TextView dollarView;
    private GlobalData globalData;
    private boolean presenterRefresh;

    public static WalletFragment newInstance() {
        WalletFragment f = new WalletFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        globalData = (GlobalData) getActivity().getApplication();
        //globalData.getPrimaryUser().setUserKey("5f073440e41311395fcc0ff5b10454040ef332b02d2caf6976231450aede0f6a");
        walletPresenter = new WalletPresenter(this, rootView.getContext(), globalData.getPrimaryUser().getUserKey());
        transactionListRecyclerView=rootView.findViewById(R.id.tx_list_recycler);
        transactionListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionAdapter=new TransactionAdapter(initDefaultTransactionList());
        transactionListRecyclerView.setAdapter(transactionAdapter);
        transactionListRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(),DividerItemDecoration.VERTICAL));
        swipeRefreshLayout=rootView.findViewById(R.id.wallet_slide_refresh);

        ethView = rootView.findViewById(R.id.balanceEth);
        dollarView = rootView.findViewById(R.id.balanceDollar);
        sendMoney=rootView.findViewById(R.id.Send);
        requestMoney=rootView.findViewById(R.id.request);

        ethView.setText("0.00 ETH");
        toAddress=null;
        presenterRefresh=false;

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(()->update());


        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMoney();
                //toAddress="0x6B96D5c8AbA7fEf48f958Cc9Bb9023DF57B85925";
                //sendMoneyBottomSheet.show();
            }
        });
        requestMoney.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requestMoney();
            }
        });
        update();
        return rootView;
    }


    void update(){
        if(globalData.getPrimaryUser().getUserKey()==""){
            Toast.makeText(getActivity(), "Please set your Ethereum private key!",
                    Toast.LENGTH_SHORT).show();
            presenterRefresh=true;
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        if(presenterRefresh){
            walletPresenter.setPrivateKey(globalData.getPrimaryUser().getUserKey());
        }
        walletPresenter.getBalance(this.getActivity());
        walletPresenter.getTransactionList(this.getActivity());
        swipeRefreshLayout.setRefreshing(false);
        setUpQRCodeBottomSheetDialog();
        setUpSendMoneyBottomSheet();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showBalance(BigDecimal balance) {
        //View rootView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_wallet,null);

        ethView.setText(balance.toString() + " ETH");
        //dollarView.setText("$"+dollarBalance.toString()+" USD");
    }

    @Override
    public void showTransactionList(List<WalletModel.Transaction> transactionList) {
        transactionAdapter=new TransactionAdapter(transactionList);
        transactionListRecyclerView.setAdapter(transactionAdapter);
    }

    @Override
    public void requestMoney() {
        if(globalData.getPrimaryUser().getUserKey()==""){
            Toast.makeText(getActivity(), "Please set your Ethereum private key!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        newQRCodeBottomSheetDialog.show();
    }

    @Override
    public void sendMoney() {
        if(globalData.getPrimaryUser().getUserKey()==""){
            Toast.makeText(getActivity(), "Please set your Ethereum private key!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        new IntentIntegrator(this.getActivity())
                .setOrientationLocked(false)
                .setCaptureActivity(CodeScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Log.i("DT","二维码识别失败");
                return;
            } else {
                String ScanResult = intentResult.getContents();
                Log.i("DT",ScanResult);
                toAddress=ScanResult;
                sendMoneyBottomSheet.show();
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
        sendMoneyBottomSheet.show();
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
    private void setUpQRCodeBottomSheetDialog() {
        newQRCodeBottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.qr_code_layout, null);
        newQRCodeBottomSheetDialog.setContentView(bottomSheetView);
        newQRCodeBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        newQRCodeBottomSheetDialog.setCancelable(true);
        newQRCodeBottomSheetDialog.setCanceledOnTouchOutside(true);

        ImageView qrImage=bottomSheetView.findViewById(R.id.qr_code);
        qrImage.setImageBitmap(walletPresenter.requestMoney());
    }


    private void setUpSendMoneyBottomSheet(){
        sendMoneyBottomSheet=new BottomSheetDialog(getActivity());
        View bottomSheetView=LayoutInflater.from(getActivity()).inflate(R.layout.send_money_layout,null);
        sendMoneyBottomSheet.setContentView(bottomSheetView);
        sendMoneyBottomSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(),android.R.color.white));
        sendMoneyBottomSheet.setCancelable(true);
        sendMoneyBottomSheet.setCanceledOnTouchOutside(true);

        EditText enterNumber=bottomSheetView.findViewById(R.id.enter_number);
        Button confirm=bottomSheetView.findViewById(R.id.confirm_sending);

        confirm.setOnClickListener(v -> {
            walletPresenter.sendMoney(toAddress,Float.parseFloat(enterNumber.getText().toString()));
            sendMoneyBottomSheet.cancel();
        });
    }
}