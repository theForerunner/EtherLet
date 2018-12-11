package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.Comment;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailedPageActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.post_detailed_page_toolbar)
    Toolbar toolbar;
    @BindView(R.id.comment_list_recycler)
    RecyclerView commentListRecyclerView;
    @BindView(R.id.post_detailed_page_floating_menu)
    FloatingActionMenu floatingActionMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detailed_page_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);

        commentListRecyclerView.setLayoutManager(new LinearLayoutManager(PostDetailedPageActivity.this));
        CommentAdapter commentAdapter = new CommentAdapter(initDefaultCommentList());
        commentListRecyclerView.setAdapter(commentAdapter);

        commentListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    floatingActionMenu.hideMenu(true);
                } else if (dy < 0) {
                    floatingActionMenu.showMenu(true);
                }
            }
        });
    }

    private class CommentHolder extends RecyclerView.ViewHolder {
        private Comment mComment;

        private CommentHolder(View itemView) {
            super(itemView);
            //TODO
        }

        private void bindComment(Comment comment) {
            mComment = comment;
            //TODO
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
        private List<Comment> commentList;

        CommentAdapter(List<Comment> commentList) {
            this.commentList = commentList;
        }

        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(PostDetailedPageActivity.this);
            View view = layoutInflater.inflate(R.layout.single_comment_card, viewGroup, false);
            return new CommentHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentHolder commentHolder, int i) {
            Comment comment = commentList.get(i);
            commentHolder.bindComment(comment);
        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private List<Comment> initDefaultCommentList() {
        List<Comment> commentList = new ArrayList<>();
        Comment comment = new Comment(1, "This is a sample content of a comment. Bla bla bla.", 1, "Commenter", new Timestamp(System.currentTimeMillis()), commentList.size() + 1, 5);
        for (int i = 0; i < 20; i++) {
            commentList.add(comment);
        }
        return commentList;
    }

    private void setUpFloatingActionBtn() {
        findViewById(R.id.btn_post_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                //TODO
            }
        });

        findViewById(R.id.btn_post_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                //TODO
            }
        });

        findViewById(R.id.btn_to_post_Top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                commentListRecyclerView.smoothScrollToPosition(0);
            }
        });

        findViewById(R.id.btn_post_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                //TODO
            }
        });
    }
}
