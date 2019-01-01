package com.example.l.EtherLet.view;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.CommentDTO;
import com.example.l.EtherLet.model.dto.PostDTO;
import com.example.l.EtherLet.model.dto.UserDTO;
import com.example.l.EtherLet.presenter.CommentPresenter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    TextView postDetailedCommentCount;
    @BindView(R.id.comment_list_swipe_refresh)
    SwipeRefreshLayout commentListSwipeRefreshLayout;
    @BindView(R.id.post_detailed_creator_image)
    CircleImageView posterImage;
    @BindView(R.id.post_detailed_creator_name)
    TextView posterName;
    @BindView(R.id.post_detailed_create_time)
    TextView postTime;
    @BindView(R.id.post_detailed_title)
    TextView postTitle;
    @BindView(R.id.post_detailed_content)
    TextView postContent;
    private BottomSheetDialog newCommentBottomSheetDialog;
    private CommentAdapter commentAdapter;
    private CommentPresenter commentPresenter;
    private PostDTO postDTO;
    GlobalData globalData;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detailed_page_layout);
        ButterKnife.bind(this);

        globalData = (GlobalData) getApplication();

        Intent intent = getIntent();
        postDTO = new PostDTO(intent.getIntExtra("postId", 1), intent.getStringExtra("postTitle"), new UserDTO(intent.getIntExtra("postCreatorId", 1), intent.getStringExtra("postCreatorName")), intent.getStringExtra("postContent"), new Timestamp(intent.getLongExtra("postCreateTime", 0)));
        Glide.with(this)
                .load(getString(R.string.host_url_real_share) + getString(R.string.download_user_image_path) + postDTO.getPostCreator().getUserId())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.outline_account_circle_black_24)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(posterImage);
        posterName.setText(postDTO.getPostCreator().getUserUsername());
        postTime.setText(getString(R.string.post_create_time_text_view_header) + " " + postDTO.getPostTime());
        postTitle.setText(postDTO.getPostTitle());
        postContent.setText(postDTO.getPostContent());

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        commentPresenter = new CommentPresenter(this);

        commentListRecyclerView.setLayoutManager(new LinearLayoutManager(PostDetailedPageActivity.this));
        commentAdapter = new CommentAdapter(initDefaultCommentList());
        commentListRecyclerView.setAdapter(commentAdapter);
        commentPresenter.loadCommentList(this, postDTO.getPostId());

        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY - oldScrollY > 10) {
                floatingActionMenu.hideMenu(true);
            } else if (oldScrollY - scrollY > 10) {
                floatingActionMenu.showMenu(true);
            }
        });

        commentListSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        commentListSwipeRefreshLayout.setOnRefreshListener(() -> commentPresenter.loadCommentList(this, postDTO.getPostId()));

        setUpBottomSheetDialog();
        setUpFloatingActionBtn(postDTO.getPostId());

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
        //@BindView(R.id.comment_seq)
        //TextView commentSeq;
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
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.outline_account_circle_black_24)
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(commenterImage);
            commenterName.setText(mCommentDTO.getCommentSender().getUserUsername());
            commentTime.setText(getString(R.string.comment_time_text_view_header) + " " + mCommentDTO.getCommentTime().toString());
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

    private void setUpFloatingActionBtn(int post_id) {
        btnComment.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            if (globalData.getPrimaryUser().getUserId() == 0) {
                Intent intent = new Intent(PostDetailedPageActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                newCommentBottomSheetDialog.show();
            }
        });

        btnToTop.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            nestedScrollView.smoothScrollTo(0, postContentLinearLayout.getTop());
        });

        btnRefresh.setOnClickListener(v -> {
            floatingActionMenu.close(true);
            commentListSwipeRefreshLayout.setRefreshing(true);
            commentPresenter.loadCommentList(PostDetailedPageActivity.this, post_id);
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
        EditText comment = bottomSheetView.findViewById(R.id.new_comment_content_edit);
        btnCancel.setOnClickListener(v -> newCommentBottomSheetDialog.cancel());
        btnPost.setOnClickListener(v -> {
            Map<String, Object> newCommentMap = new HashMap<>();
            newCommentMap.put("postId", postDTO.getPostId());
            newCommentMap.put("commentSenderId", globalData.getPrimaryUser().getUserId());
            newCommentMap.put("commentContent", comment.getText().toString());
            newCommentMap.put("commentTime", System.currentTimeMillis());
            commentPresenter.addComment(this, newCommentMap);
            newCommentBottomSheetDialog.cancel();
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
        postDetailedCommentCount.setText("" + commentDTOList.size());
        commentListSwipeRefreshLayout.setRefreshing(false);
    }
}
