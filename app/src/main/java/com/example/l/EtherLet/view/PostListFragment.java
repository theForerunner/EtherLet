package com.example.l.EtherLet.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.l.EtherLet.GlobalData;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.model.dto.PostDTO;
import com.example.l.EtherLet.model.dto.UserDTO;
import com.example.l.EtherLet.presenter.PostPresenter;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostListFragment extends Fragment implements PostListViewInterface {

    @BindView(R.id.post_list_recycler)
    RecyclerView postListRecyclerView;
    @BindView(R.id.post_list_slide_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    FloatingActionMenu floatingActionMenu;
    private PostAdapter postAdapter;
    private PostPresenter postPresenter;
    private boolean ifVisible = false;
    private BottomSheetDialog newPostBottomSheetDialog;
    private GlobalData globalData;

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
        ButterKnife.bind(this, view);

        globalData = (GlobalData) getActivity().getApplication();

        postPresenter = new PostPresenter(PostListFragment.this);

        postListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        postAdapter = new PostAdapter(initDefaultPostList());
        postListRecyclerView.setAdapter(postAdapter);
        postPresenter.loadPostList(this.getActivity());

        postListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (floatingActionMenu != null) {
                        floatingActionMenu.hideMenu(true);
                    }
                } else if (dy < 0) {
                    if (floatingActionMenu != null) {
                        floatingActionMenu.showMenu(true);
                    }
                }
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> postPresenter.loadPostList(getActivity()));

        if (getActivity() != null) {
            floatingActionMenu = getActivity().findViewById(R.id.post_list_floating_menu);
        }

        setUpBottomSheetDialog();
        setUpFloatingActionBtn();

        return view;
    }

    @Override
    public void showFailureMessage() {
        swipeRefreshLayout.setRefreshing(false);
        if (ifVisible) {
            Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showPostList(List<PostDTO> postDTOList) {
        postAdapter = new PostAdapter(postDTOList);
        postListRecyclerView.setAdapter(postAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    class PostHolder extends RecyclerView.ViewHolder{
        private PostDTO mPostDTO;

        @BindView(R.id.post_cardView)
        CardView postCardView;
        @BindView(R.id.post_creator_image)
        CircleImageView postCreatorImage;
        @BindView(R.id.post_creator_name)
        TextView postCreatorName;
        @BindView(R.id.post_create_time)
        TextView postCreateTime;
        @BindView(R.id.post_title)
        TextView postTitle;
        @BindView(R.id.post_content)
        TextView postCreateContent;

        PostHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindPost(PostDTO postDTO) {
            mPostDTO = postDTO;
            postTitle.setText(mPostDTO.getPostTitle());
            postCreatorName.setText(mPostDTO.getPostCreator().getUserUsername());
            postCreateTime.setText(getString(R.string.post_create_time_text_view_header) + " " + mPostDTO.getPostTime().toString());
            postCreateContent.setText(mPostDTO.getPostContent());
            Glide.with(getView())
                    .load(getString(R.string.host_url_real_share) + getString(R.string.download_user_image_path) + mPostDTO.getPostCreator().getUserId())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.outline_account_circle_black_24)
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(postCreatorImage);
            if (floatingActionMenu != null) {
                postCardView.setOnClickListener(v -> {
                    floatingActionMenu.close(true);
                    floatingActionMenu.hideMenu(false);
                    Intent intent = new Intent(getActivity(), PostDetailedPageActivity.class);
                    intent.putExtra("postCreatorId", mPostDTO.getPostCreator().getUserId());
                    intent.putExtra("postCreatorName", mPostDTO.getPostCreator().getUserUsername());
                    intent.putExtra("postCreateTime", mPostDTO.getPostTime());
                    intent.putExtra("postTitle", mPostDTO.getPostTitle());
                    intent.putExtra("postContent", mPostDTO.getPostContent());
                    intent.putExtra("postId", mPostDTO.getPostId());
                    startActivity(intent);
                });
            }
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder> {
        private List<PostDTO> postDTOList;

        PostAdapter(List<PostDTO> postDTOList) {
            this.postDTOList = postDTOList;
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
            PostDTO postDTO = postDTOList.get(i);
            postHolder.bindPost(postDTO);
        }

        @Override
        public int getItemCount() {
            return postDTOList.size();
        }
    }

    private void setUpFloatingActionBtn() {
        if (getActivity() != null && floatingActionMenu != null) {
            getActivity().findViewById(R.id.btn_post).setOnClickListener(v -> {
                floatingActionMenu.close(false);
                newPostBottomSheetDialog.show();
            });

            getActivity().findViewById(R.id.btn_toTop).setOnClickListener(v -> {
                floatingActionMenu.close(true);
                postListRecyclerView.smoothScrollToPosition(0);
            });

            getActivity().findViewById(R.id.btn_refresh).setOnClickListener(v -> {
                floatingActionMenu.close(true);
                swipeRefreshLayout.setRefreshing(true);
                postPresenter.loadPostList(getActivity());
            });
        }
    }

    private void setUpBottomSheetDialog() {
        newPostBottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.new_post_sheet_layout, null);
        newPostBottomSheetDialog.setContentView(bottomSheetView);
        newPostBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        newPostBottomSheetDialog.setCancelable(true);
        newPostBottomSheetDialog.setCanceledOnTouchOutside(true);

        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_new_post_cancel);
        MaterialButton btnPost = bottomSheetView.findViewById(R.id.btn_new_post);
        EditText postTitle = bottomSheetView.findViewById(R.id.new_post_title_edit);
        EditText postContent = bottomSheetView.findViewById(R.id.new_post_content_edit);
        btnCancel.setOnClickListener(v -> newPostBottomSheetDialog.cancel());
        btnPost.setOnClickListener(v -> {
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("postTitle", postTitle.getText().toString());
            postMap.put("postContent", postContent.getText().toString());
            postMap.put("postCreatorId", globalData.getPrimaryUser().getUserId());
            postMap.put("postTime", System.currentTimeMillis());
            postPresenter.addNewPost(getActivity(), postMap);
            newPostBottomSheetDialog.cancel();
        });

    }

    private List<PostDTO> initDefaultPostList() {
        List<PostDTO> defaultPostDTOList = new ArrayList<>();
        if (getActivity() != null) {
            PostDTO postDTO = new PostDTO(1, getActivity().getString(R.string.display_test_string_post_title), new UserDTO(1, "theForerunner"), getActivity().getString(R.string.display_test_string_post_content), new Timestamp(System.currentTimeMillis()));
            for (int i = 0; i < 20; i++) {
                defaultPostDTOList.add(postDTO);
            }
        }
        return defaultPostDTOList;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ifVisible = isVisibleToUser;
    }
}
