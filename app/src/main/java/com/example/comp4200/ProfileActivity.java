package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;


public class ProfileActivity extends AppCompatActivity {
    //TODO: User model to import data from
    private class User {
        Integer user_id = 1337;
        String username = "Test";
        String handle = "test";
        String first_name = "Tester";
        String last_name = "Example";
        String email = "test@example.com";
        String image_id = "someimage.png";
        String description = "This is a test profile";
        LocalDateTime created = LocalDateTime.now();
    }
    TextView name;
    TextView description;
    ImageView imageView;
    TextView handle;
    TextView date_created;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //TODO: Get intent data or shared preferences
        //TODO: Hide edit button for other peoples profiles

        name = findViewById(R.id.user_name);
        description = findViewById(R.id.user_description);
        imageView = findViewById(R.id.user_image);
        handle = findViewById(R.id.user_handle);
        date_created = findViewById(R.id.user_created);

        //TODO: Populate Text with real user data
        User user = new User();
        name.setText(user.first_name + " " + user.last_name);
        description.setText(user.description);
        handle.setText(user.handle);
        date_created.setText(user.created.getMonth() + " " + user.created.getYear());
//        imageView.setImageBitmap(bm); //TODO: Need to know how image is saved

        //TODO: Get fragments of tweets and other user aspects filtered by user
    }
}