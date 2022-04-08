package com.example.comp4200.service.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.model.User;
import com.example.comp4200.service.UserService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserServiceImpl implements UserService {

    UserDao userDao;
    DatabaseReference databaseReference;

    public UserServiceImpl() {
        userDao = new UserDao();
    }


    public void getUser(String id, SharedPreferences.Editor editor) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
        databaseReference.child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);
                if (user != null) {
                    editor.putString("id", id);
                    editor.putString("displayName", user.getDisplayName());
                    editor.putString("handle", user.getHandle());
                    editor.putString("description", user.getDescription());
                    editor.commit();
                }
            }
        });
    }

}
