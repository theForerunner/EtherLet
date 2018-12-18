package com.example.l.EtherLet.model.dto;

import java.sql.Timestamp;

public class CommentDTO {
    private int commentId;
    private String commentContent;
    private int postId;
    private UserDTO commentSender;
    private Timestamp commentTime;

    public CommentDTO(int commentId, String commentContent, int postId, UserDTO commentSender, Timestamp commentTime) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.postId = postId;
        this.commentSender = commentSender;
        this.commentTime = commentTime;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public UserDTO getCommentSender() {
        return commentSender;
    }

    public void setCommentSender(UserDTO commentSender) {
        this.commentSender = commentSender;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }
}
