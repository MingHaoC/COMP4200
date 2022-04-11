package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.service.UserService;
import com.example.comp4200.service.impl.TweetServiceImpl;
import com.example.comp4200.service.impl.UserServiceImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Check if user is logged in
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        // Recycler view and adapter
        List<Tweet> tweets = new ArrayList<>();
        TweetRecyclerAdapter adapter = new TweetRecyclerAdapter(TimelineActivity.this, tweets);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTweets);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimelineActivity.this));
        recyclerView.setAdapter(adapter);

        // save the current logged in user info to the SharedPreferences
        UserService userService = new UserServiceImpl();
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        userService.getUser(firebaseUser.getUid(), editor, obj -> {
            // Get tweet for user
            new TweetServiceImpl().getTweets(this, firebaseUser.getUid(), (Set<String>) obj, object -> {
                tweets.clear();
                if (object instanceof List<?>) {
                    for (Tweet tweet : (List<Tweet>) object)
                        tweets.add(tweet);

                    Collections.sort(tweets);

                    tweets.forEach(e-> System.out.println(new Date(e.getCreatedAt()).toString()));
                    adapter.notifyDataSetChanged();
                }
            });
        });

        // Post tweet button
        FloatingActionButton composeTweetFAB = findViewById(R.id.timeline_fabCompose);
        composeTweetFAB.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ComposeTweetActivity.class)));

        // Add listener for the image to go to profile when click
        ImageView profileImage = findViewById(R.id.timeline_imageProfile);
        profileImage.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ProfileActivity.class)));

        // Go to settings menu from timeline
        ImageButton settingsButton = findViewById(R.id.timeline_buttonSettings);
        settingsButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsActivity.class)));
    }
}