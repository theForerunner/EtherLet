package com.example.l.EtherLet.model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private String commentContent;
    private int postId;
    private String postName;
    private int commenterId;
    private String commenterName;
    private Timestamp commentDate;
    private int commentSeq;
    private int replyCount;

    public Comment(int commentId, String commentContent, int commenterId, String commenterName, Timestamp commentDate, int commentSeq, int replyCount) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.commentDate = commentDate;
        this.commentSeq = commentSeq;
        this.replyCount = replyCount;
    }

    public Comment(String commentContent, int commenterId, String commenterName, Timestamp commentDate, int commentSeq) {
        this.commentContent = commentContent;
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.commentDate = commentDate;
        this.commentSeq = commentSeq;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getSubtitle() {
        return commentContent;
    }

    public void setSubtitle(String title) {
        this.commentContent = title;
    }

    public int getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(int commenterId) {
        this.commenterId = commenterId;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public int getCommentSeq() {
        return commentSeq;
    }

    public void setCommentSeq(int commentSeq) {
        this.commentSeq = commentSeq;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
