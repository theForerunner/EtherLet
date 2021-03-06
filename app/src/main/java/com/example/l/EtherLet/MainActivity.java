package com.example.l.EtherLet;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.l.EtherLet.model.dto.User;
import com.example.l.EtherLet.view.InfoListFragment;
import com.example.l.EtherLet.view.LoginActivity;
import com.example.l.EtherLet.view.MainPagerAdapter;
import com.example.l.EtherLet.view.PostListFragment;
import com.example.l.EtherLet.view.UserDetailActivity;
import com.example.l.EtherLet.view.WalletFragment;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
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
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

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
    GlobalData globalData;
    IProfile profile;
    private long exitFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置全局变量里的用户信息
        globalData = (GlobalData) getApplication();
        globalData.setPrimaryUser(new User(0, "", "", "  未登录", ""));
        profile = new ProfileDrawerItem().withName(globalData.getPrimaryUser().getUserUsername()).withEmail(globalData.getPrimaryUser().getUserAccount()).withIcon(getString(R.string.host_url_real_share) + getString(R.string.download_user_image_path) + globalData.getPrimaryUser().getUserId()).withIdentifier(100);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        floatingActionMenu.setVisibility(View.GONE);


        setUpDrawer();
        setUpViewPager();
        refreshAccountHeader();
        tabLayout.setupWithViewPager(viewPager);
        //用于使FAB只在对应的页面上显示
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                        //这个FAB只在一个页面上显示
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

    //初始化与TabLayout绑定的ViewPager
    private void setUpViewPager() {
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.addFragment(WalletFragment.newInstance(), "Wallet");
        mainPagerAdapter.addFragment(InfoListFragment.newInstance(), "Info");
        mainPagerAdapter.addFragment(PostListFragment.newInstance(), "Forum");
        viewPager.setAdapter(mainPagerAdapter);
    }

    //初始化Drawer里的显示用户信息的部分
    private void setUpAccountHeader() {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withAccountHeader(R.layout.account_header_layout)
                .withTranslucentStatusBar(false)
                .withOnlyMainProfileImageVisible(true)
                .withTextColorRes(R.color.black_semi_transparent)
                .withCurrentProfileHiddenInList(true)
                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem().withName("Change Account").withIcon(GoogleMaterial.Icon.gmd_person_add).withIdentifier(100000)
                )
                .withOnAccountHeaderListener((view, profile, current) -> {
                    if (profile instanceof IDrawerItem) {
                        if (profile.getIdentifier() == 100000) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (profile.getIdentifier() == 100001) {
                            //TODO
                        }
                    }
                    return false;
                }).withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        //未登录则跳转到登陆界面
                        if(globalData.getPrimaryUser().getUserId()==0){
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        else {//已登录则跳转到个人详情页
                            Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                            startActivity(intent);
                            return true;
                        }
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        if(globalData.getPrimaryUser().getUserId()==0){
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                            startActivity(intent);
                            return true;
                        }
                    }
                }).withOnAccountHeaderSelectionViewClickListener((view, profile) -> false)
                .build();

    }

    //初始化Drawer
    private void setUpDrawer() {
        //设置Drawer里的显示用户信息的部分
        setUpAccountHeader();
        //使其能够通过url加载图片
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext())
                        .load(uri)
                        .apply(new RequestOptions()
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.outline_account_circle_black_24)
                                .diskCacheStrategy(DiskCacheStrategy.NONE))
                        .into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.with(imageView.getContext()).clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(64);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(64);
                }

                return super.placeholder(ctx, tag);
            }
        });
        drawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withDrawerWidthDp(250)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(GoogleMaterial.Icon.gmd_group).withName(R.string.drawer_item_friends).withIdentifier(100),
                        new SecondaryDrawerItem().withIcon(GoogleMaterial.Icon.gmd_account_balance_wallet).withName(R.string.drawer_item_wallet).withIdentifier(101)
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

    //工具栏上的按键的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer();
                break;
        }
        return true;
    }

    //用于实现后退键关闭Drawer和点击两次后退键退出
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

    //用于登陆后刷新Drawer里的用户信息
    @Override
    protected void onResume() {
        super.onResume();
        floatingActionMenu.showMenu(true);
        refreshAccountHeader();
    }

    //刷新Drawer里的用户信息
    public void refreshAccountHeader() {
        if (globalData.getPrimaryUser() != null) {
            profile = new ProfileDrawerItem().withName(globalData.getPrimaryUser().getUserUsername()).withEmail(globalData.getPrimaryUser().getUserAccount()).withIcon(getString(R.string.host_url_real_share) + getString(R.string.download_user_image_path) + globalData.getPrimaryUser().getUserId()).withIdentifier(100);
            accountHeader.updateProfile(profile);
            accountHeader.setActiveProfile(profile);
        }
    }
}
