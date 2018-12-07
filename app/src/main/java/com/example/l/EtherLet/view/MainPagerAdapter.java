package com.example.l.EtherLet.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = {"Wallet", "Info", "Billboard"};
    private ThemeListFragment themeListFragment = null;

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        themeListFragment = new ThemeListFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = CardFragment.newInstance(position);
                //break;
            case 1:
                fragment = CardFragment.newInstance(position);
                break;
            case 2:
                fragment = themeListFragment;
                break;
        }
        return fragment;
    }
}
