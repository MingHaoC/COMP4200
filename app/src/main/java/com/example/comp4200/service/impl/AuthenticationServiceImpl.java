package com.example.comp4200.service.impl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.LoginActivity;
import com.example.comp4200.TimelineActivity;
import com.example.comp4200.model.User;
import com.example.comp4200.service.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AuthenticationServiceImpl implements AuthenticationService {


    FirebaseAuth firebaseAuth;
    UserDao userDao;
    private DatabaseReference databaseReference;

    public AuthenticationServiceImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
        userDao = new UserDao();
    }

    @Override
    public void register(Context context, String email, String password, String displayName, String handle) {
        Query query = FirebaseDatabase.getInstance().getReference("Handle").child(handle);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "Handle already exists", Toast.LENGTH_LONG).show();
                    return;
                }
                  firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User();
                        user.setDisplayName(displayName);
                        user.setHandle(handle);
                        userDao.add(user, Objects.requireNonNull(task.getResult().getUser()).getUid());
                        Toast.makeText(context, "User was created successfully!", Toast.LENGTH_LONG).show();

                        // add the handle to the database to make sure no duplicate handle pops up
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        databaseReference = db.getReference("Handle");
                        databaseReference.child(handle).setValue(true);


                        context.startActivity(new Intent(context, TimelineActivity.class));
                    } else
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "An error has occurred trying to create your account please try again later", Toast.LENGTH_LONG).show();
            }
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
        Intent login = new Intent(context, LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(login);
    }

}