package com.example.l.EtherLet.model.dto;

import java.sql.Timestamp;

public class PostDTO {
    private int postId;
    private String postTitle;
    private UserDTO postCreator;
    private String postContent;
    private Timestamp postTime;

    public PostDTO(int postId, String postTitle, UserDTO postCreator, String postContent, Timestamp postTime) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postCreator = postCreator;
        this.postContent = postContent;
        this.postTime = postTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public UserDTO getPostCreator() {
        return postCreator;
    }

    public void setPostCreator(UserDTO postCreator) {
        this.postCreator = postCreator;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

}
