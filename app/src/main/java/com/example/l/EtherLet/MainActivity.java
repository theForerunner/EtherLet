package com.example.l.EtherLet;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.l.EtherLet.view.MainPagerAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip tabStrip;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private MainPagerAdapter mainPagerAdapter;
    private Drawable oldBackground;
    private int currentColor;
    private SystemBarTintManager systemBarTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        tabStrip.setViewPager(viewPager);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        viewPager.setCurrentItem(1);
        //changeColor(ContextCompat.getColor(getBaseContext(), R.color.green));

        tabStrip.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(MainActivity.this, "Tab reselected:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeColor(int newColor) {
        tabStrip.setBackgroundColor(newColor);
        systemBarTintManager.setTintColor(newColor);

        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(ContextCompat.getColor(getBaseContext(), android.R.color.transparent));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(layerDrawable);
        } else {
            TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{oldBackground, layerDrawable});
            getSupportActionBar().setBackgroundDrawable(transitionDrawable);
            transitionDrawable.startTransition(200);
        }

        oldBackground = layerDrawable;
        currentColor = newColor;
    }
}
