package com.example.l.EtherLet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.l.EtherLet.model.User;
import com.example.l.EtherLet.view.InfoListFragment;
import com.example.l.EtherLet.view.LoginActivity;
import com.example.l.EtherLet.view.MainPagerAdapter;
import com.example.l.EtherLet.view.PostListFragment;
import com.example.l.EtherLet.view.WalletFragment;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.main_page_toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.post_list_floating_menu)
    FloatingActionMenu floatingActionMenu;
    private Drawer drawer;
    AccountHeader accountHeader;

    private long exitFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GlobalData globalData = (GlobalData) getApplication();
        globalData.setPrimaryUser(new User(1, "4runn3rcn@gmail.com", "123456", "4Runn3r", "123456"));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        floatingActionMenu.setVisibility(View.GONE);

        setUpAccountHeader();
        setUpDrawer();
        setUpViewPager();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                        floatingActionMenu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (floatingActionMenu.isOpened()) {
                    floatingActionMenu.close(true);
                }
                floatingActionMenu.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setUpViewPager() {
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.addFragment(WalletFragment.newInstance(), "Wallet");
        mainPagerAdapter.addFragment(InfoListFragment.newInstance(), "Info");
        mainPagerAdapter.addFragment(PostListFragment.newInstance(), "Forum");
        viewPager.setAdapter(mainPagerAdapter);
    }

    private void setUpAccountHeader() {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withAccountHeader(R.layout.account_header_layout)
                .withTranslucentStatusBar(false)
                .withOnlyMainProfileImageVisible(true)
                .withTextColorRes(R.color.black_semi_transparent)
                .addProfiles(
                        new ProfileDrawerItem().withName("theForerunner").withEmail("4runn3rcn@gmail.com").withIcon(ContextCompat.getDrawable(this, R.drawable.my_profile)),
                        new ProfileSettingDrawerItem().withName("Add Account").withIcon(GoogleMaterial.Icon.gmd_person_add).withIdentifier(100000),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                )
                .withOnAccountHeaderListener((view, profile, current) -> {
                    if (profile instanceof IDrawerItem) {
                        if (profile.getIdentifier() == 100000) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    return false;
                }).withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }).withOnAccountHeaderSelectionViewClickListener((view, profile) -> false)
                .build();
    }

    private void setUpDrawer() {
        drawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withDrawerWidthDp(250)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(GoogleMaterial.Icon.gmd_group).withName(R.string.drawer_item_friends),
                        new SecondaryDrawerItem().withIcon(GoogleMaterial.Icon.gmd_account_balance_wallet).withName(R.string.drawer_item_wallet)
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem != null) {
                        Intent intent = null;
                        if (drawerItem.getIdentifier() == 100000) {
                            intent = new Intent(MainActivity.this, LoginActivity.class);
                        }
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }
                    return false;
                })
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else if (System.currentTimeMillis() - exitFlag > 2000) {
            Toast.makeText(this, "Press button Again to Exit", Toast.LENGTH_SHORT).show();
            exitFlag = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        floatingActionMenu.showMenu(true);
    }
}
