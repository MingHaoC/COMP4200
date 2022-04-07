package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TweetRecyclerAdapter adapter;
    ArrayList<Tweet> tweets = new ArrayList<>();

    FloatingActionButton composeTweetFAB;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        recyclerView = findViewById(R.id.recyclerViewTweets);

        String tweet1 = "This is a tweet. I am testing tweets right now :D";
        String tweet2 = "This is another tweet. I am testing another tweet right now :):):)";
        tweets.add(new Tweet(1, tweet1));
        tweets.add(new Tweet(2, tweet2));

        adapter = new TweetRecyclerAdapter(TimelineActivity.this, tweets);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimelineActivity.this));
        recyclerView.setAdapter(adapter);

        composeTweetFAB = findViewById(R.id.composeTweetFAB);
        composeTweetFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ComposeTweetActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        AuthenticationServiceImpl auth = new AuthenticationServiceImpl();
//        auth.logout(this);
        if (user == null)
            startActivity(new Intent(this, LoginActivity.class));
    }

}