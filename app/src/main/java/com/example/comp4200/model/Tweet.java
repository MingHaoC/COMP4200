package com.example.comp4200.model;

import java.util.Date;

public class Tweet {

    private String tweetId;
    private String content;
    private String displayName;
    private Date createdAt;
    private String userID;

    public Tweet(String userID, String displayName, Date createdAt, String content){
        this.userID = userID;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Tweet(String userID, String displayName, Date createdAt, String content, String tweetId){
        this.userID = userID;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.content = content;
        this.tweetId = tweetId;
    }

    public String getContent(){
        return this.content;
    }

    public String getUserId(){
        return this.userID;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public Date getDate(){
        return createdAt;
    }


}
