package com.example.comp4200.DAO;

import com.example.comp4200.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDao {

    private final DatabaseReference databaseReference;

    public UserDao() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public void add(User user, String id) {
        databaseReference.child(id).setValue(user);
    }
}
