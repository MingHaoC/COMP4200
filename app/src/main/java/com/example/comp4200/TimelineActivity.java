package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.comp4200.model.Tweet;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TweetRecyclerAdapter adapter;

    ArrayList<Tweet> tweets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("testin1");

        recyclerView = findViewById(R.id.recyclerViewTweets);

        System.out.println("testin2");

        String tweet1 = "This is a tweet. I am testing tweets right now :D";
        String tweet2 = "This is another tweet. I am testing another tweet right now :):):)";
        tweets.add(new Tweet(1, tweet1));
        tweets.add(new Tweet(2, tweet2));

        System.out.println("testin3");

        adapter = new TweetRecyclerAdapter(TimelineActivity.this, tweets);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimelineActivity.this));
        recyclerView.setAdapter(adapter);

        System.out.println("testin4");

    }



}