package com.example.comp4200.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.comp4200.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserDao {

    private final DatabaseReference databaseReference;

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

    public void get(String id, UserCallback userCallback) {
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    user.setId(snapshot.getKey());
                    userCallback.onCallback(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FAILED_USER", "getUser:onCancelled", error.toException());
            }
        });
    }

    public interface UserCallback { //Used for the async nature of the database
        void onCallback(User user);
    }
}
