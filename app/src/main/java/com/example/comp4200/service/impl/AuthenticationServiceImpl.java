package com.example.comp4200.service.impl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.LoginActivity;
import com.example.comp4200.TimelineActivity;
import com.example.comp4200.model.User;
import com.example.comp4200.service.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AuthenticationServiceImpl implements AuthenticationService {


    FirebaseAuth firebaseAuth;
    UserDao userDao = new UserDao();

    public AuthenticationServiceImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void register(Context context, String email, String password, String displayName, String handle) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // todo: check with other if we need more information to be stored in the realtime db
                User user = new User();
                user.setDisplayName(displayName);
                user.setHandle(handle);
                userDao.add(user, Objects.requireNonNull(task.getResult().getUser()).getUid());
                Toast.makeText(context, "User was created successfully!", Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context, TimelineActivity.class));
            } else
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void login(Context context, String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                context.startActivity(new Intent(context, TimelineActivity.class));
            else
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void logout(Context context) {
        firebaseAuth.signOut();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}