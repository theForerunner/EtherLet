package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.Comment;
import com.github.clans.fab.FloatingActionButton;
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
    @BindView(R.id.btn_post_comment)
    FloatingActionButton btnComment;
    @BindView(R.id.btn_to_post_Top)
    FloatingActionButton btnToTop;
    @BindView(R.id.btn_post_refresh)
    FloatingActionButton btnRefresh;
    @BindView(R.id.post_detailed_nested_scroll_page)
    NestedScrollView nestedScrollView;
    @BindView(R.id.post_detailed_header)
    LinearLayout postContentLinearLayout;
    private BottomSheetDialog newCommentBottomSheetDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detailed_page_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        commentListRecyclerView.setLayoutManager(new LinearLayoutManager(PostDetailedPageActivity.this));
        CommentAdapter commentAdapter = new CommentAdapter(initDefaultCommentList());
        commentListRecyclerView.setAdapter(commentAdapter);

        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 10) {
                    floatingActionMenu.hideMenu(true);
                } else if (oldScrollY - scrollY > 10) {
                    floatingActionMenu.showMenu(true);
                }
            }
        });
        setUpBottomSheetDialog();
        setUpFloatingActionBtn();
        commentListRecyclerView.setFocusable(false);
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
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                newCommentBottomSheetDialog.show();
            }
        });

        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                nestedScrollView.smoothScrollTo(0, postContentLinearLayout.getTop());
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
            }
        });
    }

    private void setUpBottomSheetDialog() {
        newCommentBottomSheetDialog = new BottomSheetDialog(PostDetailedPageActivity.this);
        View bottomSheetView = LayoutInflater.from(PostDetailedPageActivity.this).inflate(R.layout.new_comment_sheet_layout, null);
        newCommentBottomSheetDialog.setContentView(bottomSheetView);
        newCommentBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(PostDetailedPageActivity.this, android.R.color.transparent));
        newCommentBottomSheetDialog.setCancelable(true);
        newCommentBottomSheetDialog.setCanceledOnTouchOutside(true);

        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_new_comment_cancel);
        MaterialButton btnPost = bottomSheetView.findViewById(R.id.btn_new_comment);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCommentBottomSheetDialog.cancel();
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (floatingActionMenu != null && floatingActionMenu.isShown()) {
            if (floatingActionMenu.isOpened()) {
                floatingActionMenu.close(false);
            }
            floatingActionMenu.hideMenu(false);
        }
        super.onBackPressed();
    }
}
