package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.fragment.TweetFragment;
import com.example.comp4200.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView description;
    TextView handle;
    TextView date_created;
    Button settingsButton, followButton, likedTweetsButton, postedTweetsButton, returnButton, hideTweetsButton;
    boolean following = false;
    View user_forums;
    private User profileUser;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getLoggedInUser();
        setViews();

        name = findViewById(R.id.profile_username);
        description = findViewById(R.id.profile_desc);
        handle = findViewById(R.id.profile_handle);
        date_created = findViewById(R.id.profile_joindate);
        followButton = findViewById(R.id.profile_follow);
        likedTweetsButton = findViewById(R.id.profile_likedTweets);
        postedTweetsButton = findViewById(R.id.profile_postedTweets);
        settingsButton = findViewById(R.id.profile_settings);
        user_forums = findViewById(R.id.user_forums);
        Group profileGroup = findViewById(R.id.profileGroup); // for easily showing/hiding stuff

        String userId = getIntent().getStringExtra("user_id");
        if (userId == null || userId.isEmpty()) {
            userId = currentUser != null ? currentUser.getUid() : "";
            date_created.setText(new Date(currentUser.getMetadata().getCreationTimestamp()).toString());
        }
        String finalUserId = userId;
        new UserDao().get(userId, user -> {
            profileUser = user;
            profileUser.setId(finalUserId);
            name.setText(profileUser.getDisplayName());
            description.setText(profileUser.getDescription());
            handle.setText(profileUser.getHandle());

            // NOTE: This needs to be called to show the follow and settings buttons
            if (currentUser.getUid().equals(finalUserId)) { //If the user is looking at their own profile
                settingsButton.setVisibility(View.VISIBLE);
                followButton.setVisibility(View.INVISIBLE);
                // Go to the settings menu from your own profile
            } else //if the user is looking at someone else's profile
            {
                settingsButton.setVisibility(View.INVISIBLE);
                followButton.setVisibility(View.VISIBLE);
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

        //this if-statement doesn't work until we populate user object with actual data
//        if (this.userId.equals(user.getId())){  //need current user
//            followButton.setVisibility(View.INVISIBLE);
//        }
        followButton.setOnClickListener(view -> {
            if (following)
                unfollowUser();
            else
                followUser();
        });

        // Go to timeline from profile
        returnButton = findViewById(R.id.profile_return);
        returnButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), TimelineActivity.class)));

        postedTweetsButton = findViewById(R.id.profile_postedTweets);
        postedTweetsButton.setOnClickListener(new View.OnClickListener() {  //TODO: doesn't distinguish between liked/posted tweets yet
            @Override
            public void onClick(View view) {
                getTweets(new View(getApplicationContext()));
                user_forums.setVisibility(View.VISIBLE);
                hideTweetsButton.setVisibility(View.VISIBLE);
                profileGroup.setVisibility(View.INVISIBLE); //hides the rest of the profile menu
            }
        });

        likedTweetsButton = findViewById(R.id.profile_likedTweets);
        likedTweetsButton.setOnClickListener(new View.OnClickListener() { //TODO: doesn't distinguish between liked/posted tweets yet
            @Override
            public void onClick(View view) {
                getLikedTweets(new View(getApplicationContext()));
                user_forums.setVisibility(View.VISIBLE);
                hideTweetsButton.setVisibility(View.VISIBLE);
                profileGroup.setVisibility(View.INVISIBLE); //hides the rest of the profile menu
            }
        });

        hideTweetsButton = findViewById(R.id.profile_hideTweets);
        hideTweetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_forums.setVisibility(View.INVISIBLE);
                hideTweetsButton.setVisibility(View.INVISIBLE);
                profileGroup.setVisibility(View.VISIBLE); //shows the rest of the profile menu again
            }
        });

        // When the profile is opened, hide the tweets until the user wants to see them
        hideTweetsButton.setVisibility(View.INVISIBLE);
        user_forums.setVisibility(View.INVISIBLE);

    }

    public void getTweets(View view) {
        TweetFragment tweetFragment = TweetFragment.newInstance(profileUser.getId(), false);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_forums, tweetFragment);
        ft.commit();
    }

    public void getLikedTweets(View view) {
        TweetFragment likedTweetFragment = TweetFragment.newInstance(profileUser.getId(), true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_forums, likedTweetFragment);
        ft.commit();
    }

    //TODO: connect to DB
    //functions to interact w DB
    public void unfollowUser() {
        followButton.setText("Follow");
        Toast.makeText(this, "Unfollowed user @" + handle, Toast.LENGTH_SHORT).show();
        following = false;
    }

    public void followUser() {
        followButton.setText("Unfollow");
        Toast.makeText(this, "Followed user @" + handle, Toast.LENGTH_SHORT).show();
        following = true;
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