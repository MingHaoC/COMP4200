package com.example.comp4200;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComposeTweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        //change this button to a FAB later
        Button tweetButton = findViewById(R.id.compose_buttonSubmit);

        /*
         * grabs the text from the editText box on the page, when they click the tweet button it
         * provides an error message if there are any, and sends the user back to the main page/timeline
         * if there are no errors and they provided a valid tweet.
         * @param view
         */
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText composeTweet = findViewById(R.id.compose_etextComposition);
                String tweetText = composeTweet.getText().toString();

                TextView errorText = findViewById(R.id.compose_textError);

                String error;

                if(tweetText.isEmpty()){
                    error = "Tweet cannot be empty";

                }else if(tweetText.length() > 280){
                    error = "Max tweet length is 280 characters.";
                }else{
                    error = "";
                    //send tweet to db
                    //then send user back to home page/timeline
                    startActivity(new Intent(view.getContext(), MainActivity.class));
                }

                errorText.setText(error);

            }
        });

    }
}
