package com.example.comp4200.service.impl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.MainActivity;
import com.example.comp4200.model.User;
import com.example.comp4200.service.AuthenticationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AuthenticationServiceImpl implements AuthenticationService {


    FirebaseAuth firebaseAuth;
    UserDao userDao = new UserDao();

    public AuthenticationServiceImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void register(Context context, String displayName, String handle, String email, String password, String description) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // todo: check with other if we need more information to be stored in the realtime db
                User user = new User();
                user.setDisplayName(displayName);
                user.setHandle(handle);
                user.setDescription(description);
                userDao.add(user, Objects.requireNonNull(task.getResult().getUser()).getUid());
                Toast.makeText(context, "User was created successfully!", Toast.LENGTH_LONG).show();
                // context.startActivity(new Intent(context, Login.class));
            } else
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void login(Context context, String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                context.startActivity(new Intent(context, MainActivity.class));
            else
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void logout(Context context) {
        firebaseAuth.signOut();
        // context.startActivity(new Intent(context, Login.class));
    }

}
