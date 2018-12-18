package com.example.l.EtherLet.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.CommentDTO;
import com.example.l.EtherLet.model.dto.UserDTO;
import com.example.l.EtherLet.presenter.CommentPresenter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailedPageActivity extends AppCompatActivity implements CommentListViewInterface{
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
    @BindView(R.id.post_detailed_comment_count)
    TextView postDetailedCommentCountTextView;
    @BindView(R.id.comment_list_swipe_refresh)
    SwipeRefreshLayout commentListSwipeRefreshLayout;
    private BottomSheetDialog newCommentBottomSheetDialog;
    private CommentAdapter commentAdapter;
    private CommentPresenter commentPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detailed_page_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        commentPresenter = new CommentPresenter(this);

        commentListRecyclerView.setLayoutManager(new LinearLayoutManager(PostDetailedPageActivity.this));
        commentAdapter = new CommentAdapter(initDefaultCommentList());
        commentListRecyclerView.setAdapter(commentAdapter);
        commentPresenter.loadCommentList(this);

        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY - oldScrollY > 10) {
                floatingActionMenu.hideMenu(true);
            } else if (oldScrollY - scrollY > 10) {
                floatingActionMenu.showMenu(true);
            }
        });

        commentListSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        commentListSwipeRefreshLayout.setOnRefreshListener(() -> commentPresenter.loadCommentList(this));

        setUpBottomSheetDialog();
        setUpFloatingActionBtn();

        commentListRecyclerView.setFocusable(false);
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        private CommentDTO mCommentDTO;

        @BindView(R.id.commenter_image)
        CircleImageView commenterImage;
        @BindView(R.id.commenter_name)
        TextView commenterName;
        @BindView(R.id.comment_time)
        TextView commentTime;
        @BindView(R.id.comment_seq)
        TextView commentSeq;
        @BindView(R.id.comment_content)
        TextView commentContent;

        private CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindComment(CommentDTO commentDTO) {
            mCommentDTO = commentDTO;
            Glide.with(PostDetailedPageActivity.this)
                    .load(getString(R.string.host_url_real_share) + getString(R.string.download_user_image_path) + mCommentDTO.getCommentSender().getUserId())
                    .apply(new RequestOptions().placeholder(R.drawable.my_profile))
                    .into(commenterImage);
            commenterName.setText(mCommentDTO.getCommentSender().getUserUsername());
            commentTime.setText(getString(R.string.comment_time_text_view_header) + mCommentDTO.getCommentTime().toString());
            commentContent.setText(mCommentDTO.getCommentContent());
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
        private List<CommentDTO> commentDTOList;

        CommentAdapter(List<CommentDTO> commentDTOList) {
            this.commentDTOList = commentDTOList;
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
            CommentDTO commentDTO = commentDTOList.get(i);
            commentHolder.bindComment(commentDTO);
        }

        @Override
        public int getItemCount() {
            return commentDTOList.size();
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

    private List<CommentDTO> initDefaultCommentList() {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        CommentDTO commentDTO = new CommentDTO(1, "This is a sample content of a commentDTO. Bla bla bla.", 1, new UserDTO(1, "theForerunner"), new Timestamp(System.currentTimeMillis()));
        for (int i = 0; i < 20; i++) {
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    private void setUpFloatingActionBtn() {
        btnComment.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            newCommentBottomSheetDialog.show();
        });

        btnToTop.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            nestedScrollView.smoothScrollTo(0, postContentLinearLayout.getTop());
        });

        btnRefresh.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            commentListSwipeRefreshLayout.setRefreshing(true);
            commentPresenter.loadCommentList(PostDetailedPageActivity.this);
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

    @Override
    public void showFailureMessage() {
        commentListSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCommentList(List<CommentDTO> commentDTOList) {
        commentAdapter = new CommentAdapter(commentDTOList);
        commentListRecyclerView.setAdapter(commentAdapter);
        postDetailedCommentCountTextView.setText(commentDTOList.size());
        commentListSwipeRefreshLayout.setRefreshing(false);
    }
}
