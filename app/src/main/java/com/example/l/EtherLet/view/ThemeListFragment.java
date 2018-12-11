package com.example.l.EtherLet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.Theme;
import com.example.l.EtherLet.presenter.ThemePresenter;
import com.github.clans.fab.FloatingActionMenu;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ThemeListFragment extends Fragment implements ThemeViewInterface{
    private RecyclerView themeRecyclerView;
    private ThemeAdapter themeAdapter;
    private ThemePresenter themePresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionMenu floatingActionsMenu;

    public static ThemeListFragment newInstance() {
        ThemeListFragment themeListFragment = new ThemeListFragment();
        Bundle args = new Bundle();
        themeListFragment.setArguments(args);
        return themeListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theme_list_fragment, container, false);

        themePresenter = new ThemePresenter(ThemeListFragment.this);
        themeRecyclerView = view.findViewById(R.id.theme_recycler);
        themeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        themeAdapter = new ThemeAdapter(initDefaultThemeList());
        themeRecyclerView.setAdapter(themeAdapter);
        themePresenter.loadThemeList(this.getActivity());
        themeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    floatingActionsMenu.hideMenu(true);
                } else if (dy < 0) {
                    floatingActionsMenu.showMenu(true);
                }
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.theme_list_slide_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                themePresenter.loadThemeList(getActivity());
            }
        });

        floatingActionsMenu = getActivity().findViewById(R.id.theme_list_floating_menu);

        setUpFloatingActionBtn();

        return view;
    }

    @Override
    public void showFailureMessage() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showThemeList(List<Theme> themeList) {
        themeAdapter = new ThemeAdapter(themeList);
        themeRecyclerView.setAdapter(themeAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    private class ThemeHolder extends RecyclerView.ViewHolder{
        private Theme mTheme;

        private CardView postCardView;
        private TextView themeSubtitleTextView;
        private TextView themeCreatorTextView;
        private TextView themeCreateDateTextView;
        private ShineButton btnComment;
        private ShineButton btnShare;

        private ThemeHolder(View itemView) {
            super(itemView);
            postCardView = itemView.findViewById(R.id.post_cardView);
            themeSubtitleTextView = itemView.findViewById(R.id.post_title);
            themeCreatorTextView = itemView.findViewById(R.id.post_creator_and_time);
            themeCreateDateTextView = itemView.findViewById(R.id.post_content);
            btnComment = itemView.findViewById(R.id.btn_comment);
            btnShare = itemView.findViewById(R.id.btn_share);
        }

        private void bindTheme(Theme theme) {
            mTheme = theme;
            themeSubtitleTextView.setText(mTheme.getSubtitle());
            themeCreatorTextView.setText("Posted by " + mTheme.getCreatorName() + " at " + mTheme.getCreateDate());
            themeCreateDateTextView.setText(R.string.display_test_string_post_content);
            btnComment.setShapeResource(R.drawable.baseline_comment_black_18dp);
            btnShare.setShapeResource(R.drawable.baseline_share_black_18dp);
            btnComment.init(getActivity());
            btnShare.init(getActivity());

            postCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                }
            });

            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private class ThemeAdapter extends RecyclerView.Adapter<ThemeHolder> {
        private List<Theme> themeList;

        ThemeAdapter(List<Theme> themeList) {
            this.themeList = themeList;
        }

        @NonNull
        @Override
        public ThemeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.single_theme_card, viewGroup, false);
            return new ThemeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ThemeHolder themeHolder, int i) {
            Theme theme = themeList.get(i);
            themeHolder.bindTheme(theme);
        }

        @Override
        public int getItemCount() {
            return themeList.size();
        }
    }

    private void setUpFloatingActionBtn() {
        getActivity().findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.close(false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        getActivity().findViewById(R.id.btn_toTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.close(true);
                themeRecyclerView.smoothScrollToPosition(0);
            }
        });

        getActivity().findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.close(true);
                swipeRefreshLayout.setRefreshing(true);
                themePresenter.loadThemeList(getActivity());
            }
        });
    }

    private List<Theme> initDefaultThemeList() {
        List<Theme> defaultThemeList = new ArrayList<>();
        Theme theme = new Theme(1, getActivity().getString(R.string.display_test_string_post_title), 1, "Creator", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        for (int i = 0; i < 20; i++) {
            defaultThemeList.add(theme);
        }
        return defaultThemeList;
    }
}
