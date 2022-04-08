package com.example.comp4200.service;

import android.content.Context;

import com.example.comp4200.model.User;

public interface TweetService {

    void PostTweet(Context context, String content, User user);
}
