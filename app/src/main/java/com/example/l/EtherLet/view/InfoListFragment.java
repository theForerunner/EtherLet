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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.presenter.InfoPresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InfoListFragment extends Fragment implements InfoListViewInterface {

    private RecyclerView infoRecyclerView;
    private InfoAdapter infoAdapter;
    private InfoPresenter infoPresenter;
    private SwipeRefreshLayout infoSwipeRefreshLayout;
    private List<CoinInfo> infoList;
    private TextView range;
    private LinearLayout rangeLayout;
    private ListPopupWindow rangeWindow;
    private boolean click=false;
    private ArrayList<String> rangeList=new ArrayList<>();
    private String rangeSelect;
    private boolean ifVisible = false;


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

        range = view.findViewById(R.id.text_info_title_range);
        rangeLayout = view.findViewById(R.id.info_text_range_button_layout);
        initRangeList();
        rangeSelect = "Month";
        rangeWindow = new ListPopupWindow(getActivity());

        infoPresenter = new InfoPresenter(InfoListFragment.this);

        infoRecyclerView = view.findViewById(R.id.info_recycler);
        infoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        infoRecyclerView.addItemDecoration(itemDecoration);

        infoAdapter = new InfoAdapter(initDefaultInfoList());
        infoRecyclerView.setAdapter(infoAdapter);
        infoPresenter.loadInfoList(getActivity());

        infoSwipeRefreshLayout = view.findViewById(R.id.info_list_slide_refresh);
        infoSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        infoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                infoPresenter.loadInfoList(getActivity());
            }
        });

        rangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!click){
                    rangeWindow.setAdapter(new ArrayAdapter(getActivity(),R.layout.history_list,rangeList));
                    rangeWindow.setDropDownGravity(Gravity.END);
                    rangeWindow.setAnchorView(rangeLayout);
                    rangeWindow.show();
                    click=true;
                }
                else click=false;

            }
        });
        rangeWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                range.setText(rangeList.get(position));
                rangeSelect=rangeList.get(position);
                infoPresenter.loadInfoList(getActivity());
                rangeWindow.dismiss();
                click=false;
            }
        });
        return view;
    }


    @Override
    public void initInfoList(List<CoinInfo> list)
    {
        infoAdapter = new InfoAdapter(list);
        infoRecyclerView.setAdapter(infoAdapter);
        infoSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateInfoList(List<CoinInfo> list){
        infoList=list;
        infoAdapter.notifyDataSetChanged();
        infoSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFailMessage(){
        infoSwipeRefreshLayout.setRefreshing(false);
        if (ifVisible) {
            Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
        }
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
            if(info.getSymbol().equals("")){
                symbolTextView.setText("");
                nameTextView.setText("");
                priceTextView.setText("");
                highTextView.setText("");
                lowTextView.setText("");
                highPriceTextView.setText("");
                lowPriceTextView.setText("");
                changeTextView.setText("");
            }
            else
            {
                symbolTextView.setText(coinInfo.getSymbol());
                nameTextView.setText(coinInfo.getName());
                priceTextView.setText(convertPrice(coinInfo.getPriceUSD()));
                highPriceTextView.setText(convertPrice(coinInfo.getHigh()));
                lowPriceTextView.setText(convertPrice(coinInfo.getLow()));

                String changeRange="";
                if(rangeSelect.equals("Month")){
                    changeRange=coinInfo.getChangeMonthly();
                }
                else if(rangeSelect.equals("Week")){
                    changeRange=coinInfo.getChangeWeekly();
                }
                else if(rangeSelect.equals("Daily")){
                    changeRange=coinInfo.getChangeDaily();
                }
                else if(rangeSelect.equals("Hour")){
                    changeRange=coinInfo.getChangeHour();
                }
                Double change=Double.parseDouble(changeRange)*100;
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

        /*@Override
        public long getItemId(int position){
            return infoList.get(position).hashCode();
        }*/
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
        info.setSymbol("");
        info.setName("");
        info.setPriceUSD("");
        info.setHigh("");
        info.setLow("");
        info.setChangeMonthly("");
        for (int i = 0; i < 100; i++) {
            defaultInfoList.add(info);
        }
        return defaultInfoList;
    }

    public void initRangeList(){
        rangeList.add("Hour");
        rangeList.add("Daily");
        rangeList.add("Week");
        rangeList.add("Month");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ifVisible = isVisibleToUser;
    }
}
