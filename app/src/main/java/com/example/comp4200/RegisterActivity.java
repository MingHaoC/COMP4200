package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp4200.service.AuthenticationService;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;

public class RegisterActivity extends AppCompatActivity {
    AuthenticationService authenticationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authenticationService = new AuthenticationServiceImpl();

        EditText email = findViewById(R.id.register_email);
        EditText password = findViewById(R.id.register_password);
        EditText displayName = findViewById(R.id.register_name);
        EditText handle = findViewById(R.id.register_handle);
        Button submit = findViewById(R.id.register_submit);
        TextView goToLogin = findViewById(R.id.register_gotoLogin);

        submit.setOnClickListener(view -> {
            // get input
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();
            String displayNameString = displayName.getText().toString();
            String handleString = handle.getText().toString();

            // validate inputs
            if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailString).matches()) {
                Toast.makeText(this, "Invalid email input", Toast.LENGTH_LONG).show();
                return;
            }
            if (password.length() < 8) {
                Toast.makeText(this, "Password length needs to be greater than or equal to 8", Toast.LENGTH_LONG).show();
                return;
            }

            if (displayNameString.length() < 1) {
                Toast.makeText(this, "Display name cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }

            // Todo: Check if handle already exists
            if (handleString.length() < 1) {
                Toast.makeText(this, "Handle cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }

            authenticationService.register(this, emailString, passwordString, displayNameString, handleString);
        });

        goToLogin.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

    }
}