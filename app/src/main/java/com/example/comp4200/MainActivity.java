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

    AuthenticationService authenticationService = new AuthenticationServiceImpl();
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toLoginButton = findViewById(R.id.login_button_main);

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });

        Button toComposeTweet = findViewById(R.id.tweet_button_main);

        toComposeTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ComposeTweetActivity.class));
            }
        });
    }

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
            // todo: update this so it goes to the login activity instead of staying on the main screen
            System.out.println("Not login");
        else
            System.out.println("Logged in");
    }

}