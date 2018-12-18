package com.example.l.EtherLet.model.dto;

public class FriendDTO {
    private int friendshipId;
    private int userId;
    private String userUsername;
    private String userKey;

    public FriendDTO(int friendshipId, int userId, String userUsername, String userKey) {
        this.friendshipId = friendshipId;
        this.userId = userId;
        this.userUsername = userUsername;
        this.userKey = userKey;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
