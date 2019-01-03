package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.example.l.EtherLet.model.dto.CoinInfo;
import com.example.l.EtherLet.presenter.InfoPresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

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
    private boolean clickDetail=false;
    private ArrayList<String> rangeList=new ArrayList<>();
    private String rangeSelect;
    private ArrayList<String> detailRangeList=new ArrayList<>();
    private String detailRangeSelect;
    private boolean ifVisible = false;
    private List<com.github.mikephil.charting.data.CandleEntry> candleEntryList=new ArrayList<>();
    private CandleStickChart candleStickChart;
    private String dataSet;
    private BottomSheetDialog infoDetailBottomSheetDialog;

    public static InfoListFragment newInstance() {
        InfoListFragment infoListFragment = new InfoListFragment();
        Bundle args = new Bundle();
        infoListFragment.setArguments(args);
        return infoListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_list_fragment, container, false);//绑定控件信息
        range = view.findViewById(R.id.text_info_title_range);
        rangeLayout = view.findViewById(R.id.info_text_range_button_layout);
        initRangeList();
        rangeSelect = "Month";
        rangeWindow = new ListPopupWindow(getActivity());
        initDetailRangeList();
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
        rangeLayout.setOnClickListener(new View.OnClickListener() {//设置范围下拉弹窗
            @Override
            public void onClick(View v) {
                if(!click){
                    rangeWindow.setAdapter(new ArrayAdapter(getActivity(),R.layout.popup_list,rangeList));
                    rangeWindow.setDropDownGravity(Gravity.END);
                    rangeWindow.setAnchorView(rangeLayout);
                    rangeWindow.show();
                    click=true;
                }
                else click=false;

            }
        });
        rangeWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//初始化范围下拉菜单
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
    public void initInfoList(List<CoinInfo> list)//初始化货币信息列表
    {
        infoAdapter = new InfoAdapter(list);
        infoRecyclerView.setAdapter(infoAdapter);
        infoSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateInfoList(List<CoinInfo> list){//更新货币信息列表
        infoList=list;
        infoAdapter.notifyDataSetChanged();
        infoSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFailMessage(){//显示错误信息
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



        private InfoHolder(View itemView) {//绑定item控件
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

        private void bindInfo(CoinInfo info) {//绑定item信息
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
                constraintLayout.setOnClickListener(new View.OnClickListener() {//设置K线图弹窗
                    @Override
                    public void onClick(View v) {

                        infoDetailBottomSheetDialog = new BottomSheetDialog(getActivity());
                        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.info_detail_sheet_layout, null);
                        infoDetailBottomSheetDialog.setContentView(bottomSheetView);
                        infoDetailBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
                        infoDetailBottomSheetDialog.setCancelable(false);
                        infoDetailBottomSheetDialog.setCanceledOnTouchOutside(true);
                        TextView symbolText=bottomSheetView.findViewById(R.id.info_detail_symbol);
                        TextView volumeText=bottomSheetView.findViewById(R.id.info_detail_volume);
                        symbolText.setText(info.getSymbol());
                        volumeText.setText(info.getVolume());
                        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_info_detail_cancel);
                        LinearLayout detailRangeLayout=bottomSheetView.findViewById(R.id.info_detail_range_button_layout);
                        TextView detailRange=bottomSheetView.findViewById(R.id.text_info_detail_range);
                        candleStickChart=bottomSheetView.findViewById(R.id.candler_chart);
                        detailRangeSelect="5m";
                        detailRange.setText(detailRangeSelect);
                        ListPopupWindow detailRangeWindow=new ListPopupWindow(getActivity());
                        dataSet=info.getName();
                        initCandleChart(info.getSymbol(),"bitfinex",detailRangeSelect,candleStickChart);
                        detailRangeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!clickDetail){
                                    detailRangeWindow.setAdapter(new ArrayAdapter(getActivity(),R.layout.popup_list,detailRangeList));
                                    detailRangeWindow.setDropDownGravity(Gravity.END);
                                    detailRangeWindow.setAnchorView(detailRangeLayout);
                                    detailRangeWindow.show();
                                    clickDetail=true;
                                }
                                else clickDetail=false;

                            }
                        });
                        detailRangeWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                detailRange.setText(detailRangeList.get(position));
                                detailRangeSelect=detailRangeList.get(position);
                                infoPresenter.getCandleEntryList(info.getSymbol(),"bitfinex",detailRangeSelect,getActivity());
                                detailRangeWindow.dismiss();
                                clickDetail=false;
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                infoDetailBottomSheetDialog.cancel();
                            }
                        });
                        infoDetailBottomSheetDialog.show();
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

    }

    private String convertPrice(String price){//价格转换
        Double priceDouble=Double.parseDouble(price);
        DecimalFormat df=new DecimalFormat("0.00");
        String convertPrice=df.format(priceDouble)+"$";
        return convertPrice;
    }

    public List<CoinInfo> initDefaultInfoList() {//默认列表
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

    public void initRangeList(){//货币涨跌范围列表
        rangeList.add("Hour");
        rangeList.add("Daily");
        rangeList.add("Week");
        rangeList.add("Month");
    }
    public void initDetailRangeList(){//k线图范围列表
        detailRangeList.add("5m");
        detailRangeList.add("15m");
        detailRangeList.add("30m");
        detailRangeList.add("1h");
        detailRangeList.add("6h");
        detailRangeList.add("1d");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ifVisible = isVisibleToUser;
    }

    public void initCandleChart(String symbol,String trader,String _enum,CandleStickChart mChart){//初始化K线图表配置信息
        mChart.setDrawBorders(true);
        mChart.setBorderWidth(1);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setNoDataText("Now Loading Data......Please Click to Refresh");
        Description description=new Description();
        description.setText("");
        mChart.setDescription(description);
        XAxis xAxis =mChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setLabelCount(7,false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(false);
        YAxis rightAxis =mChart.getAxisRight();
        rightAxis.setEnabled(false);
        infoPresenter.getCandleEntryList(symbol,trader,_enum,getActivity());
    }

    @Override
    public void setCandleEntryList(List<CandleEntry> list){//设置K线图数据信息

        if(!list.isEmpty()){
            candleEntryList=list;
            CandleDataSet set = new CandleDataSet(candleEntryList, dataSet);
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setShadowColor(Color.DKGRAY);
            set.setShadowColorSameAsCandle(true);
            set.setShadowWidth(0.7f);//影线
            set.setDecreasingColor(Color.GREEN);
            set.setDecreasingPaintStyle(Paint.Style.FILL);
            set.setIncreasingColor(Color.RED);
            set.setIncreasingPaintStyle(Paint.Style.STROKE);
            set.setNeutralColor(Color.RED);
            set.setHighlightLineWidth(1f);
            set.setDrawValues(false);
            CandleData data = new CandleData(set);
            candleStickChart.setData(data);
        }
        else {
            candleStickChart.setNoDataText("Sorry,No Valid KLine Data ");
        }
    }
}
