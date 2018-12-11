package com.example.l.EtherLet.model;

import java.sql.Timestamp;

public class Post {
    private int postId;
    private String subtitle;
    private int creatorId;
    private String creatorName;
    private Timestamp createDate;
    private int commentCount;

    public Post(int postId, String subtitle, int creatorId, String creatorName, Timestamp createDate, int commentCount) {
        this.postId = postId;
        this.subtitle = subtitle;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createDate = createDate;
        this.commentCount = commentCount;
    }

    public Post(String subtitle, int creatorId, String creatorName, Timestamp createDate) {
        this.subtitle = subtitle;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createDate = createDate;
    }

    public int getPostId() {
        return postId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
