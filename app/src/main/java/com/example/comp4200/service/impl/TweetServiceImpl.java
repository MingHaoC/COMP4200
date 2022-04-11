package com.example.comp4200.service.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.model.User;
import com.example.comp4200.service.FirebaseCallback;
import com.example.comp4200.service.TweetService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class TweetServiceImpl implements TweetService {

    private final DatabaseReference databaseReference;

    public TweetServiceImpl() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Tweet.class.getSimpleName());
    }

    @Override
    public void PostTweet(Context context, String content, User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        databaseReference.child(user.getId()).child(UUID.randomUUID().toString()).setValue(new Tweet(user.getId(), user.getDisplayName(), timestamp.getTime(), content)).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Toast.makeText(context, "Tweet was posted successfully", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Error: tweet could not be posted", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void getUserTweets(Context context, String id, FirebaseCallback callback) {
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Tweet> tweets = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Tweet tweet = ds.getValue(Tweet.class);
                        new LikesService().getLikes(ds.getKey(), obj -> {
                            tweet.setLikes((Map<String, Boolean>) obj);
                            tweet.setTweetId(ds.getKey());
                            tweets.add(tweet);
                            callback.onCallback(tweets);
                        });
                    }
                } else
                    Toast.makeText(context, "Could not fetch the tweets", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TWEET_GET_ID", "onCancelled: ", error.toException());
            }
        });
    }

    @Override
    public void getTweets(Context context, String id, Set<String> followers, FirebaseCallback callback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Tweet> tweets = new ArrayList<>();
                    for (DataSnapshot tweetSnapShot : snapshot.getChildren())
                        if (followers.contains(tweetSnapShot.getKey()))
                            for (DataSnapshot ds : tweetSnapShot.getChildren()) {
                                Tweet tweet = ds.getValue(Tweet.class);

                                if (tweet != null) {
                                    new LikesService().getLikes(ds.getKey(), obj -> {
                                        tweet.setLikes((Map<String, Boolean>) obj);
                                        tweet.setTweetId(ds.getKey());
                                        if (tweets.contains(tweet)) {
                                            for (int i = 0; i < tweets.size(); i++) {
                                                if (tweets.get(i).compareTo(tweet) == 0) {
                                                    tweets.set(i, tweet);
                                                }
                                            }
                                        } else {
                                            tweets.add(tweet);
                                        }
                                        callback.onCallback(tweets);
                                    });
                                }
                            }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TWEET_GET_ID", "onCancelled: ", error.toException());
            }
        });
    }
}
