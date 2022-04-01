package com.example.comp4200;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;

public class LoginActivity extends AppCompatActivity {

    AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        authenticationService = new AuthenticationServiceImpl();

        /*
         * grabs the username/email and password from the edit texts and checks to see if the user
         * has provided a valid login, uses if statement to provide errors and send the user to
         * main page/timeline if the login was valid
         *
         * @param view
         */
        Button loginButton = findViewById(R.id.login_buttonLogin);
        loginButton.setOnClickListener(view -> {
            EditText username_email_text = findViewById(R.id.login_etextUsername);
            EditText password_text = findViewById(R.id.login_etextPassword);

            String email = username_email_text.getText().toString();
            String password = password_text.getText().toString();

            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "email and password cannot be blank", Toast.LENGTH_LONG).show();

            authenticationService.login(this, email, password);
        });

        TextView registerText = findViewById(R.id.login_textDonthave);
        registerText.setOnClickListener(view ->
                startActivity(new Intent(view.getContext(), RegisterActivity.class))
        );

    }

}
