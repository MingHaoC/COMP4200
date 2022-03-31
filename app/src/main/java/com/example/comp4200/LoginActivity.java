package com.example.comp4200;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        /*
         * grabs the username/email and password from the edit texts and checks to see if the user
         * has provided a valid login, uses if statement to provide errors and send the user to
         * main page/timeline if the login was valid
         *
         * @param view
         */
        Button loginButton = findViewById(R.id.register_buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username_email_text = findViewById(R.id.register_etextUsername);
                EditText password_text =  findViewById(R.id.register_etextPassword);
                TextView error_text = findViewById(R.id.register_textError);

                String username_email = username_email_text.getText().toString();
                String password = password_text.getText().toString();

                String error;

                System.out.println("error_message: " + error_text);

                if (username_email.isEmpty() || password.isEmpty()) {
                    error = "Please enter your email/username and password to login.";
                } else {
                    error = "";
                    startActivity(new Intent(view.getContext(), MainActivity.class));
                }

               error_text.setText(error);

//              still need to check if the user is in the database.
//              assign error message appropriately
            }
        });



        TextView registerText = findViewById(R.id.register_textDonthave);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send to register page
                //startActivity(new Intent(view.getContext(), RegisterActivity.class));
            }
        });

    }

}
