package com.example.comp4200;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp4200.model.User;
import com.example.comp4200.service.TweetService;
import com.example.comp4200.service.impl.TweetServiceImpl;

public class ComposeTweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_tweet);

        TweetService tweetService = new TweetServiceImpl();


        //change this button to a FAB later
        Button tweetButton = findViewById(R.id.compose_buttonSubmit);

        /*
         * grabs the text from the editText box on the page, when they click the tweet button it
         * provides an error message if there are any, and sends the user back to the main page/timeline
         * if there are no errors and they provided a valid tweet.
         * @param view
         */
        tweetButton.setOnClickListener(view -> {

            EditText composeTweet = findViewById(R.id.compose_etextComposition);
            String tweetText = composeTweet.getText().toString();

            if (tweetText.isEmpty())
                Toast.makeText(view.getContext(), "Tweet cannot be empty", Toast.LENGTH_LONG).show();
            else if (tweetText.length() > 280)
                Toast.makeText(view.getContext(), "Max tweet length is 280 characters.", Toast.LENGTH_LONG).show();
            else {
                SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                User user = new User(sp.getString("id", ""), sp.getString("displayName", ""));
                tweetService.PostTweet(ComposeTweetActivity.this, tweetText, user);
                startActivity(new Intent(view.getContext(), TimelineActivity.class));
            }


        });

    }
}
