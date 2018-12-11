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
import com.example.l.EtherLet.model.Post;
import com.example.l.EtherLet.presenter.PostPresenter;
import com.github.clans.fab.FloatingActionMenu;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostListFragment extends Fragment implements PostListViewInterface {
    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private PostPresenter postPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionMenu floatingActionsMenu;

    public static PostListFragment newInstance() {
        PostListFragment postListFragment = new PostListFragment();
        Bundle args = new Bundle();
        postListFragment.setArguments(args);
        return postListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_list_fragment, container, false);

        postPresenter = new PostPresenter(PostListFragment.this);
        postRecyclerView = view.findViewById(R.id.post_recycler);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postAdapter = new PostAdapter(initDefaultPostList());
        postRecyclerView.setAdapter(postAdapter);
        postPresenter.loadPostList(this.getActivity());
        postRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        swipeRefreshLayout = view.findViewById(R.id.post_list_slide_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postPresenter.loadPostList(getActivity());
            }
        });

        floatingActionsMenu = getActivity().findViewById(R.id.post_list_floating_menu);

        setUpFloatingActionBtn();

        return view;
    }

    @Override
    public void showFailureMessage() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPostList(List<Post> postList) {
        postAdapter = new PostAdapter(postList);
        postRecyclerView.setAdapter(postAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    private class PostHolder extends RecyclerView.ViewHolder{
        private Post mPost;

        private CardView postCardView;
        private TextView postSubtitleTextView;
        private TextView postCreatorTextView;
        private TextView postCreateDateTextView;
        private ShineButton btnComment;
        private ShineButton btnShare;

        private PostHolder(View itemView) {
            super(itemView);
            postCardView = itemView.findViewById(R.id.post_cardView);
            postSubtitleTextView = itemView.findViewById(R.id.post_title);
            postCreatorTextView = itemView.findViewById(R.id.post_creator_and_time);
            postCreateDateTextView = itemView.findViewById(R.id.post_content);
            btnComment = itemView.findViewById(R.id.btn_comment);
            btnShare = itemView.findViewById(R.id.btn_share);
        }

        private void bindPost(Post post) {
            mPost = post;
            postSubtitleTextView.setText(mPost.getSubtitle());
            postCreatorTextView.setText("Posted by " + mPost.getCreatorName() + " at " + mPost.getCreateDate());
            postCreateDateTextView.setText(R.string.display_test_string_post_content);
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

    private class PostAdapter extends RecyclerView.Adapter<PostHolder> {
        private List<Post> postList;

        PostAdapter(List<Post> postList) {
            this.postList = postList;
        }

        @NonNull
        @Override
        public PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.single_post_card, viewGroup, false);
            return new PostHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostHolder postHolder, int i) {
            Post post = postList.get(i);
            postHolder.bindPost(post);
        }

        @Override
        public int getItemCount() {
            return postList.size();
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
                postRecyclerView.smoothScrollToPosition(0);
            }
        });

        getActivity().findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.close(true);
                swipeRefreshLayout.setRefreshing(true);
                postPresenter.loadPostList(getActivity());
            }
        });
    }

    private List<Post> initDefaultPostList() {
        List<Post> defaultPostList = new ArrayList<>();
        Post post = new Post(1, getActivity().getString(R.string.display_test_string_post_title), 1, "Creator", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        for (int i = 0; i < 20; i++) {
            defaultPostList.add(post);
        }
        return defaultPostList;
    }
}
