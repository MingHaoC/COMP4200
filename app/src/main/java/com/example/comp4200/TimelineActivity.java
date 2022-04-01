package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TweetRecyclerAdapter adapter;
    ArrayList<Tweet> tweets = new ArrayList<>();

    AuthenticationService authenticationService = new AuthenticationServiceImpl();
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null)
            startActivity(new Intent(this, LoginActivity.class));
    }

}