package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.model.Post;
import com.example.l.EtherLet.presenter.InfoPresenter;
import com.example.l.EtherLet.presenter.PostPresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InfoListFragment extends Fragment implements InfoListViewInterface {

    private RecyclerView infoRecyclerView;
    private InfoAdapter infoAdapter;
    private InfoPresenter infoPresenter;
    private SwipeRefreshLayout infoSwipeRefreshLayout;


    public static InfoListFragment newInstance() {
        InfoListFragment infoListFragment = new InfoListFragment();
        Bundle args = new Bundle();
        infoListFragment.setArguments(args);
        return infoListFragment;
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_list_fragment, container, false);

        infoPresenter = new InfoPresenter(InfoListFragment.this);
        infoRecyclerView = view.findViewById(R.id.info_recycler);
        infoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL);
        infoRecyclerView.addItemDecoration(itemDecoration);
        infoSwipeRefreshLayout = view.findViewById(R.id.info_list_slide_refresh);
        infoSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        infoAdapter = new InfoAdapter(initDefaultInfoList());
        infoRecyclerView.setAdapter(infoAdapter);
        infoPresenter.loadInfoList();
        infoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                infoPresenter.loadInfoList();
            }
        });
        return view;
    }

    @Override
    public void showInfoList(List<CoinInfo> list)
    {
        infoAdapter = new InfoAdapter(list);
        infoRecyclerView.setAdapter(infoAdapter);
        infoSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showFailMessgae(){

    }

    private class InfoHolder extends RecyclerView.ViewHolder{
        private CoinInfo coinInfo;
        private ConstraintLayout constraintLayout;
        private TextView symbolTextView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView highTextView;
        private TextView lowTextView;
        private TextView highPriceTextView;
        private TextView lowPriceTextView;
        private TextView changeTextView;




        private InfoHolder(View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.info_constraint_layout);
            symbolTextView=itemView.findViewById(R.id.info_text_symbol);
            nameTextView=itemView.findViewById(R.id.info_text_name);
            priceTextView=itemView.findViewById(R.id.info_text_price_usd);
            highTextView=itemView.findViewById(R.id.info_text_24high);
            lowTextView=itemView.findViewById(R.id.info_text_24low);
            highPriceTextView=itemView.findViewById(R.id.info_text_price_24_high);
            lowPriceTextView=itemView.findViewById(R.id.info_text_price_24_low);
            changeTextView=itemView.findViewById(R.id.info_text_change);
        }

        private void bindInfo(CoinInfo info) {
            coinInfo=info;
            symbolTextView.setText(coinInfo.getSymbol());
            nameTextView.setText(coinInfo.getName());
            priceTextView.setText(convertPrice(coinInfo.getPriceUSD()));
            highPriceTextView.setText(convertPrice(coinInfo.getHigh()));
            lowPriceTextView.setText(convertPrice(coinInfo.getLow()));
            Double change=Double.parseDouble(coinInfo.getChangeMonthly())*100;
            boolean flag=false;
            if(change>=0){
                flag=true;
                changeTextView.setTextColor(getResources().getColor(R.color.red_for_price_rise));
            }
            else {
                changeTextView.setTextColor(getResources().getColor(R.color.green_for_price_drop));

            }
            DecimalFormat df=new DecimalFormat("0.00");
            String changeString=df.format(change)+"%";
            if(flag){
               changeString="+"+changeString;
            }
            changeTextView.setText(changeString);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
    private class InfoAdapter extends RecyclerView.Adapter<InfoHolder> {
        private List<CoinInfo> infoList;

        InfoAdapter(List<CoinInfo> infoList) {
            this.infoList = infoList;
        }

        @NonNull
        @Override
        public InfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.info_item, viewGroup, false);
            return new InfoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull InfoHolder postHolder, int i) {
            CoinInfo info= infoList.get(i);
            postHolder.bindInfo(info);
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }

    private String convertPrice(String price){
        Double priceDouble=Double.parseDouble(price);
        DecimalFormat df=new DecimalFormat("0.00");
        String convertPrice=df.format(priceDouble)+"$";
        return convertPrice;
    }

    public List<CoinInfo> initDefaultInfoList() {
        List<CoinInfo> defaultInfoList = new ArrayList<>();
        CoinInfo info = new CoinInfo();
        info.setSymbol("BTC");
        info.setName("bitcoin");
        info.setPriceUSD("8134.85601071");
        info.setHigh("8368.41733741");
        info.setLow("7972.64470958");
        info.setChangeMonthly("0.008");
        for (int i = 0; i < 100; i++) {
            defaultInfoList.add(info);
        }
        return defaultInfoList;
    }

}
