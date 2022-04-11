package com.example.comp4200.service.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.model.User;
import com.example.comp4200.service.FirebaseCallback;
import com.example.comp4200.service.UserService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {

    UserDao userDao;
    DatabaseReference databaseReference;

    public UserServiceImpl() {
        userDao = new UserDao();
    }


    public void getUser(String id, SharedPreferences.Editor editor, FirebaseCallback firebaseCallback) {
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

        databaseReference = db.getReference("UserFollows");
        databaseReference.child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Set<String> followers = new HashSet<>();
                HashMap<String, Boolean> map = (HashMap<String, Boolean>) task.getResult().getValue();
                followers.add(id);
                if (map != null) {
                    map.forEach((key, value) -> followers.add(key));
                    editor.putStringSet("followers", followers);
                }
                firebaseCallback.onCallback(followers);
            }
        });
    }

}
