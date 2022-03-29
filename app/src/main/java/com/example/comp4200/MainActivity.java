package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    AuthenticationService authenticationService;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authenticationService = new AuthenticationServiceImpl();

        Button toComposeTweet = findViewById(R.id.tweet_button_main);

        toComposeTweet.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ComposeTweetActivity.class)));

        firebaseAuth = FirebaseAuth.getInstance();
        // authenticationService.register(this,"Test", "test", "chen1fl@uwindsor.ca", "password123!", "I am a new twitter user");

        // authenticationService.login(this, "chen1fl@uwindsor.ca", "password123!");

        // authenticationService.logout(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null)
            startActivity(new Intent(this, LoginActivity.class));
        else
            System.out.println("Logged in");
    }
}