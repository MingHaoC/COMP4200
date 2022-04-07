package com.example.comp4200.DAO;

import com.example.comp4200.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserDao {

    private DatabaseReference databaseReference;

    public UserDao() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public void add(User user, String id) {
        databaseReference.child(id).setValue(user);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashmap) {
        return databaseReference.child(key).updateChildren(hashmap);
    }
}
