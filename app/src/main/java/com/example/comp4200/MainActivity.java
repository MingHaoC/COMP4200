package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    AuthenticationService authenticationService = new AuthenticationServiceImpl();
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        // authenticationService.register("Test", "test", "chen1fl@uwindsor.ca", "password123!", "I am a new twitter user");


    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user == null)
//            // todo: update this so it goes to the login activity instead of staying on the main screen
//            System.out.println("Not login");
    }

}