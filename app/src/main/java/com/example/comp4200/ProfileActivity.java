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

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.fragment.TweetFragment;
import com.example.comp4200.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView description;
    ImageView imageView;
    TextView handle;
    TextView date_created;
    Button editButton, followButton;
    boolean following = false;

    private User profileUser;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getLoggedInUser();
        setViews();

        name = findViewById(R.id.user_name);
        description = findViewById(R.id.user_description);
        imageView = findViewById(R.id.user_image);
        handle = findViewById(R.id.user_handle);
        date_created = findViewById(R.id.user_created);
        editButton = findViewById(R.id.edit_profile);
        followButton = findViewById(R.id.followButton);

        String userId = getIntent().getStringExtra("user_id");
        if (userId == null || userId.isEmpty()) {
            userId = currentUser != null ? currentUser.getUid() : "";
        }
        String finalUserId = userId;
        new UserDao().get(userId, user -> {
            profileUser = user;
            profileUser.setId(finalUserId);
            name.setText(profileUser.getDisplayName());
            description.setText(profileUser.getDescription());
            handle.setText(profileUser.getHandle());
            if (currentUser.getUid().equals(finalUserId)) {
                editButton.setVisibility(View.VISIBLE);
            }
            getTweets(new View(getApplicationContext()));
        });
//        date_created.setText(user.getCreatedDate().getMonth() + " " + user.getCreatedDate().getYear());
//        imageView.setImageBitmap(bm); //TODO: Need to know how image is saved

        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
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
        TweetFragment tweetFragment = TweetFragment.newInstance(profileUser.getId());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_forums, tweetFragment);
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

    protected void getLoggedInUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null)
            startActivity(new Intent(this, LoginActivity.class));
    }

    protected void setViews() {
        name = findViewById(R.id.user_name);
        description = findViewById(R.id.user_description);
        imageView = findViewById(R.id.user_image);
        handle = findViewById(R.id.user_handle);
        date_created = findViewById(R.id.user_created);
        editButton = findViewById(R.id.edit_profile);
    }
}