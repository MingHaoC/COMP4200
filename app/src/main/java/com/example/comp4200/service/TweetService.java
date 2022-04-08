package com.example.comp4200.service;

import android.content.Context;

import com.example.comp4200.TweetRecyclerAdapter;
import com.example.comp4200.model.Tweet;
import com.example.comp4200.model.User;

import java.util.ArrayList;

public interface TweetService {

    void PostTweet(Context context, String content, User user);

    void getTweets(Context context, String id,  ArrayList<Tweet> tweets, TweetRecyclerAdapter adapter);
}
