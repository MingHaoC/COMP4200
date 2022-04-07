package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp4200.fragment.Tweet;
import com.example.comp4200.model.User;

import java.time.LocalDateTime;


public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView description;
    ImageView imageView;
    TextView handle;
    TextView date_created;
    Button editButton, followButton;
    boolean following = false;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //TODO: Get intent data or shared preferences

        name = findViewById(R.id.user_name);
        description = findViewById(R.id.user_description);
        imageView = findViewById(R.id.user_image);
        handle = findViewById(R.id.user_handle);
        date_created = findViewById(R.id.user_created);
        editButton = findViewById(R.id.edit_profile);
        followButton = findViewById(R.id.followButton);

        //TODO: Populate Text with real user data
        User user = new User();
        this.userId = user.getId();
        name.setText(user.getDisplayName());
        description.setText(user.getDescription());
        handle.setText(user.getHandle());

        // date_created.setText(user.getCreatedDate().getMonth() + " " + user.getCreatedDate().getYear());
//        imageView.setImageBitmap(bm); //TODO: Need to know how image is saved
        //TODO: Hide edit button on other peoples profiles
        if (this.userId == user.getId()){ //need current user
            editButton.setVisibility(View.VISIBLE);
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });



        //TODO: connect to DB
        //check if the user is already following the person
        //set the button to say Unfollow if they are following already
        //or follow if they are not following the user

        //this if-statement doesn't work until we populate user object with actual data
//        if (this.userId.equals(user.getId())){  //need current user
//            followButton.setVisibility(View.INVISIBLE);
//        }
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(following){
                    unfollowUser();
                }else{
                    followUser();
                }
            }
        });
    }

    public void getTweets(View view) {
        Tweet tweetFragment = Tweet.newInstance(userId);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.user_forums,tweetFragment);
        ft.commit();
    }

    //TODO: connect to DB
    //functions to interact w DB
    public void unfollowUser(){
        followButton.setText("Follow");
        Toast.makeText(this, "Unfollowed user @" + handle , Toast.LENGTH_SHORT).show();
        following = false;
    }

    public void followUser(){
        followButton.setText("Unfollow");
        Toast.makeText(this, "Followed user @" + handle , Toast.LENGTH_SHORT).show();
        following = true;
    }


}