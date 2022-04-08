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
import java.util.List;
import java.util.UUID;

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
    public void getTweets(Context context, FirebaseCallback callback) {
        //TODO: Get tweets of most recent
    }

    @Override
    public void getTweets(Context context, String id, FirebaseCallback callback) {
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Tweet> tweets = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren())
                        tweets.add(ds.getValue(Tweet.class));
                    callback.onCallback(tweets);
                } else
                    Toast.makeText(context, "Could not fetch the tweets", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TWEET_GET_ID", "onCancelled: ", error.toException());
            }
        });
    }
}
