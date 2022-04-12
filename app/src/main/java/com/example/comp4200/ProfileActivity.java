package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.fragment.TweetFragment;
import com.example.comp4200.model.User;
import com.example.comp4200.service.UserService;
import com.example.comp4200.service.impl.UserServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView description;
    TextView handle;
    TextView date_created;
    Button settingsButton, followButton, likedTweetsButton, postedTweetsButton, returnButton;
    String userId;
    Set<String> followers;
    UserService userService;

    private User profileUser;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getLoggedInUser();
        setViews();

        userService = new UserServiceImpl();

        name = findViewById(R.id.profile_username);
        description = findViewById(R.id.profile_desc);
        handle = findViewById(R.id.profile_handle);
        date_created = findViewById(R.id.profile_joindate);
        followButton = findViewById(R.id.profile_follow);
        likedTweetsButton = findViewById(R.id.profile_likedTweets);
        postedTweetsButton = findViewById(R.id.profile_postedTweets);
        settingsButton = findViewById(R.id.profile_settings);

        userId = getIntent().getStringExtra("user_id");
        if (userId == null || userId.isEmpty())
            userId = currentUser != null ? currentUser.getUid() : "";

        String finalUserId = userId;
        new UserDao().get(userId, user -> {
            profileUser = user;
            profileUser.setId(finalUserId);
            name.setText(profileUser.getDisplayName());
            description.setText(profileUser.getDescription());
            handle.setText(profileUser.getHandle());
            // NOTE: This needs to be called to show the follow and settings buttons
            //If the user is looking at their own profile
            if (currentUser.getUid().equals(finalUserId)) {
                settingsButton.setVisibility(View.VISIBLE);
                followButton.setVisibility(View.INVISIBLE);
                date_created.setText(new Date(currentUser.getMetadata().getCreationTimestamp()).toString());
                // Go to the settings menu from your own profile
            } else {  //if the user is looking at someone else's profile
                settingsButton.setVisibility(View.INVISIBLE);
                followButton.setVisibility(View.VISIBLE);
                date_created.setVisibility(TextView.INVISIBLE);
            }

            getTweets(new View(getApplicationContext()));
        });


        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        //TODO: connect to DB
        //check if the user is already following the person
        //set the button to say Unfollow if they are following already
        //or follow if they are not following the user
        followers = getSharedPreferences("user", MODE_PRIVATE).getStringSet("followers", new HashSet<>());

        if(followers.contains(userId))
            followButton.setText("Unfollow");
        followButton.setOnClickListener(view -> {
            if (followers.contains(userId))
                unfollowUser(currentUser.getUid(), userId);
            else
                followUser(currentUser.getUid(), userId);

        });

        // Go to timeline from profile
        returnButton = findViewById(R.id.profile_return);
        returnButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), TimelineActivity.class)));
    }

    public void getTweets(View view) {
        TweetFragment tweetFragment = TweetFragment.newInstance(profileUser.getId());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_forums, tweetFragment);
        ft.commit();
    }

    private void updateList(String id) {
        userService.getFollowers(id, getSharedPreferences("userFollower", MODE_PRIVATE).edit(), obj -> {
            if(obj instanceof Set<?>)
                followers = (Set<String>) obj;
        });
    }

    public void unfollowUser(String currentUserId, String removedUserId) {

        userService.removeFollower(currentUserId, removedUserId, success -> {
            if(success instanceof Boolean) {
                if((Boolean) success){
                    followButton.setText("follow");
                    Toast.makeText(this, "Unfollowed user @" + handle, Toast.LENGTH_SHORT).show();
                    updateList(currentUserId);
                } else
                    Toast.makeText(this, "An error has occurred trying to unfollow the user, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void followUser(String currentUserId, String addedUserId) {
        userService.addFollower(currentUserId, addedUserId, success -> {
            if(success instanceof  Boolean) {
                if((Boolean) success){
                    followButton.setText("Unfollow");
                    Toast.makeText(this, "Followed user @" + handle, Toast.LENGTH_SHORT).show();
                    updateList(currentUserId);
                } else
                    Toast.makeText(this, "An error has occurred trying to follow the user, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void getLoggedInUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null)
            startActivity(new Intent(this, LoginActivity.class));
    }

    protected void setViews() {
        name = findViewById(R.id.profile_username);
        description = findViewById(R.id.profile_desc);
        handle = findViewById(R.id.profile_handle);
        date_created = findViewById(R.id.profile_joindate);
    }
}