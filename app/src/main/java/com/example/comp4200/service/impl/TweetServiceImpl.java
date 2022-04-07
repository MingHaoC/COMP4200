package com.example.comp4200.service.impl;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.comp4200.TweetRecyclerAdapter;
import com.example.comp4200.model.Tweet;
import com.example.comp4200.model.User;
import com.example.comp4200.service.TweetService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    public void getTweets(Context context, String id, ArrayList<Tweet> tweets, TweetRecyclerAdapter adapter) {

        // get the current user tweets tweets
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        tweets.add( dataSnapshot.getValue(Tweet.class));
                } else
                    Toast.makeText(context, "Could not fetch the tweets", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Server Error: Could not fetch the tweets", Toast.LENGTH_LONG).show();
            }
        });

        // get following tweets

    }

}
