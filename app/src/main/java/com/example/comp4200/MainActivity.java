package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.model.User;
import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    AuthenticationService authenticationService = new AuthenticationServiceImpl();
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();



//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("displayName", "test edited");
//        userDao.update("-MzHR2ce473GUYMKEdsj", hashMap);

        authenticationService.register("Test", "test", "shinypichu11@hotmail.com", "password123!");


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