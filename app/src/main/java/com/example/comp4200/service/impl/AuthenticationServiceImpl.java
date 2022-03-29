package com.example.comp4200.service.impl;

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

public class AuthenticationServiceImpl implements AuthenticationService {


    FirebaseAuth firebaseAuth;
    UserDao userDao = new UserDao();

    // todo: Encrypt password using Bcrypt (not sure if we need to go that far since it's just gonna be a basic project)
    @Override
    public void register(String displayName, String handle, String email, String password) {

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    // todo: check with other if we need more information to be stored in the realtime db
                        User user = new User();
                        user.setDisplayName(displayName);
                        user.setHandle(handle);
                        user.setPassword(password);
                        userDao.add(user, task.getResult().getUser().getUid());
                } else {
                    // todo: output toast message stating that the user was not created successfully
                    System.out.println("User not created Successfully : " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void login(String email, String password) {

    }

}
