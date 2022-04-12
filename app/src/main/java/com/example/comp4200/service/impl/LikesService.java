package com.example.comp4200.service.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.service.FirebaseCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikesService {
    private final DatabaseReference databaseReference;

    public LikesService() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("TweetLikes");
    }

    public void getLikes(String tweetId, FirebaseCallback callback) {
        databaseReference.child(tweetId).orderByValue().equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Boolean> likes = new HashMap<>();
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        likes.put(ds.getKey(), (Boolean) ds.getValue());
                    }
                }
                callback.onCallback(likes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("LIKES_GET", "onCancelled: ", error.toException());
            }
        });
    }

    public void getLikedTweets(String uId, FirebaseCallback callback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> userLiked = new ArrayList();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (((Map<String, Boolean>) ds.getValue()).containsKey(uId)) {
                            userLiked.add(ds.getKey());
                        }
                    }
                    callback.onCallback(userLiked);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("USER_LIKES_GET", "onCancelled: ", error.toException());
            }
        });
    }

    public void likeTweet(String tweetId, String uId) {
        databaseReference.child(tweetId).child(uId).setValue(true);
    }

    public void unlikeTweet(String tweetId, String uId) {
        databaseReference.child(tweetId).child(uId).setValue(false);
    }
}
