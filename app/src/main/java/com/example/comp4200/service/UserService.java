package com.example.comp4200.service;

import android.content.SharedPreferences;

import com.example.comp4200.model.User;
import com.google.firebase.database.FirebaseDatabase;

public interface UserService {

    void getUser(String id, SharedPreferences.Editor editor, FirebaseCallback firebaseCallback);

    void addFollower(String currentUserId, String addedUserId, FirebaseCallback callback);

    void removeFollower(String currentUserId, String removedUserId, FirebaseCallback callback);

    void getFollowers(String id, SharedPreferences.Editor editor, FirebaseCallback firebaseCallback);
}
