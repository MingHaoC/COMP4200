package com.example.comp4200.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tweet implements Comparable {

    private String tweetId;
    private String content;
    private String displayName;
    private Long createdAt;
    private String userID;
    private Map<String, Boolean> likes;

    public Tweet() {
    }

    public Tweet(String content, String displayName) {
        this.content = content;
        this.displayName = displayName;
    }

    public Tweet(String userID, String displayName, Long createdAt, String content) {
        this.userID = userID;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Tweet(String userID, String displayName, Long createdAt, String content, String tweetId) {
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

    public String getUserId() {
        return userID;
    }

    public void setUserId(String userId) {
        this.userID = userId;
    }

    public Map<String, Boolean> getLikes() { return likes; }

    public void setLikes(Map<String, Boolean> likes) { this.likes = likes; }

    @Override
    public int compareTo(Object tweet) {
        return new Date(((Tweet) tweet).getCreatedAt()).compareTo(new Date(this.getCreatedAt()));
    }
}
