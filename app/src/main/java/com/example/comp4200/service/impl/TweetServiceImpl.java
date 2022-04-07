package com.example.comp4200.service.impl;

import android.content.Context;
import android.widget.Toast;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.model.User;
import com.example.comp4200.service.TweetService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;

public class TweetServiceImpl implements TweetService {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    public TweetServiceImpl() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Tweet.class.getSimpleName());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void PostTweet(Context context, String content, User user) {
        databaseReference.child(user.getId()).child(UUID.randomUUID().toString()).setValue(new Tweet(user.getId(), user.getDisplayName(), new Date(), content)).addOnCompleteListener(task -> {
            if(task.isSuccessful())
                Toast.makeText(context, "Tweet was posted successfully", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Error: tweet could not be posted", Toast.LENGTH_LONG).show();
        });

    }
}
