package com.example.comp4200.service;

import android.content.Context;

import com.example.comp4200.model.User;

import java.util.Set;

public interface TweetService {

    void PostTweet(Context context, String content, User user);

    void getUserTweets(Context context, String id, FirebaseCallback callback);

    void getTweets(Context context, String id, Set<String> followers, FirebaseCallback callback);
}
