package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        Button logout = findViewById(R.id.mainActivity_logout);

        toComposeTweet.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ComposeTweetActivity.class)));
        logout.setOnClickListener(view -> authenticationService.logout(this));
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