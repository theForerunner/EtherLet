package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l.EtherLet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    @BindView(R.id.balanceEth)
    TextView balanceEth;

    @BindView(R.id.balanceDollar)
    TextView balanceDollar;

    private int position;

    public static WalletFragment newInstance(int position) {
        WalletFragment f = new WalletFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet,container,false);
        ButterKnife.bind(this, rootView);
        ViewCompat.setElevation(rootView, 50);
        //textView.setText("CARD " + position);
        return rootView;
    }
}
