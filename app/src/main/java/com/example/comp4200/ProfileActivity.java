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

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.fragment.Tweet;
import com.example.comp4200.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView description;
    ImageView imageView;
    TextView handle;
    TextView date_created;
    Button editButton;

    private User profileUser;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        isLoggedIn();
        setViews();

        String userId = getIntent().getStringExtra("user_id");
        if (userId == null || userId.isEmpty()) {
            userId = currentUser != null ? currentUser.getUid() : "";
        }
        new UserDao().get(userId, user -> {
            profileUser = user;
            name.setText(profileUser.getDisplayName());
            description.setText(profileUser.getDescription());
            handle.setText(profileUser.getHandle());

            if (currentUser.getUid().equals(profileUser.getId())) {
                editButton.setVisibility(View.VISIBLE);
            }
        });
//        date_created.setText(user.getCreatedDate().getMonth() + " " + user.getCreatedDate().getYear());
//        imageView.setImageBitmap(bm); //TODO: Need to know how image is saved

        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });
    }

    public void getTweets(View view) {
        Tweet tweetFragment = Tweet.newInstance(profileUser.getId());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.user_forums, tweetFragment);
        ft.commit();
    }

    protected void isLoggedIn() {
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