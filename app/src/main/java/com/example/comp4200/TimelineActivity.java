package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.model.Tweet;
import com.example.comp4200.model.User;
import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.UserService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.example.comp4200.service.impl.UserServiceImpl;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TweetRecyclerAdapter adapter;
    ArrayList<Tweet> tweets = new ArrayList<>();
    ImageView profileImage;

    FloatingActionButton composeTweetFAB;

    FirebaseAuth firebaseAuth;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        recyclerView = findViewById(R.id.recyclerViewTweets);
        profileImage = findViewById(R.id.imageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfileActivity.class));
            }
        });

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
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        
        userService = new UserServiceImpl();
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        userService.getUser(firebaseUser.getUid(), editor);

    }

}