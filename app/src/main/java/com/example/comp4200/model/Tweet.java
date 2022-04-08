package com.example.comp4200.model;

import java.sql.Timestamp;

public class Tweet {

    private String tweetId;
    private String content;
    private String displayName;
    private Long createdAt;
    private String userID;

    public Tweet() {}

    public Tweet(String content, String displayName) {
        this.content = content;
        this.displayName = displayName;
    }

    public Tweet(String userID, String displayName, Long createdAt, String content){
        this.userID = userID;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Tweet(String userID, String displayName, Long createdAt, String content, String tweetId){
        this.userID = userID;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.content = content;
        this.tweetId = tweetId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
