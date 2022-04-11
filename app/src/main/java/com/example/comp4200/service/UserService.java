package com.example.comp4200.service;

import android.content.SharedPreferences;

import com.example.comp4200.model.User;

public interface UserService {

    void getUser(String id, SharedPreferences.Editor editor, FirebaseCallback firebaseCallback);

}
