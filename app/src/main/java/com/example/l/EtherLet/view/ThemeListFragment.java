package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.Theme;
import com.example.l.EtherLet.presenter.ThemePresenter;

import java.util.ArrayList;
import java.util.List;

public class ThemeListFragment extends Fragment implements ThemeViewInterface{
    private RecyclerView themeRecyclerView;
    private ThemeAdapter themeAdapter;
    private ThemePresenter themePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theme_recycler, container, false);

        themePresenter = new ThemePresenter(ThemeListFragment.this);
        themeRecyclerView = view.findViewById(R.id.theme_recycler);
        themeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        themeAdapter = new ThemeAdapter(new ArrayList<Theme>());
        themeRecyclerView.setAdapter(themeAdapter);
        themePresenter.loadThemeList(this.getActivity());
        return view;
    }
    @Override
    public void showThemeList(List<Theme> themeList) {
        themeAdapter = new ThemeAdapter(themeList);
        themeRecyclerView.setAdapter(themeAdapter);
    }

    private class ThemeHolder extends RecyclerView.ViewHolder{
        private Theme mTheme;

        private TextView themeSubtitleTextView;
        private TextView themeCreatorTextView;
        private TextView themeCreateDateTextView;

        private ThemeHolder(View itemView) {
            super(itemView);
            themeSubtitleTextView = itemView.findViewById(R.id.theme_subtitle);
            themeCreatorTextView = itemView.findViewById(R.id.theme_creator);
            themeCreateDateTextView = itemView.findViewById(R.id.theme_create_date);
        }

        private void bindTheme(Theme theme) {
            mTheme = theme;
            themeSubtitleTextView.setText(mTheme.getSubtitle());
            themeCreatorTextView.setText(mTheme.getCreatorName());
            themeCreateDateTextView.setText(mTheme.getCreateDate().toString());

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
}
