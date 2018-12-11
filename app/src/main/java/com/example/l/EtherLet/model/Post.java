package com.example.l.EtherLet.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String subtitle;
    private int creatorId;
    private String creatorName;
    private Timestamp createDate;
    private Timestamp replyDate;

    public Post(int id, String subtitle, int creatorId, String creatorName, Timestamp createDate, Timestamp replyDate) {
        this.id = id;
        this.subtitle = subtitle;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createDate = createDate;
        this.replyDate = replyDate;
    }

    public Post(String subtitle, int creatorId, String creatorName, Timestamp createDate, Timestamp replyDate) {
        this.subtitle = subtitle;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createDate = createDate;
        this.replyDate = replyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Timestamp replyDate) {
        this.replyDate = replyDate;
    }

}
