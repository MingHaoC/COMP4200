package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.comp4200.DAO.UserDao;
import com.example.comp4200.model.User;
import com.example.comp4200.service.impl.AuthenticationServiceImpl;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    TextView userName;
    TextView userHandle;
    TextView userDescription;
    EditText nameText;
    EditText handleText;
    EditText descriptionText;
    Button logoutButton;
    Button returnButton;
    Button profileButton;
    private User currentUser;
    Button usernameSubmit;
    Button handleSubmit;
    Button descriptionSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FirebaseUser firebaseUser = getLoggedInUser();

        userName = findViewById(R.id.user_name);
        userHandle = findViewById(R.id.user_handle);
        userDescription = findViewById(R.id.user_description);
        nameText = findViewById(R.id.edit_name);
        handleText = findViewById(R.id.edit_handle);
        descriptionText = findViewById(R.id.edit_description);
        logoutButton = findViewById(R.id.logout_button);

        new UserDao().get(firebaseUser.getUid(), user -> {
            currentUser = user;
            currentUser.setId(firebaseUser.getUid());
            userName.setText(currentUser.getDisplayName());
            userHandle.setText(currentUser.getHandle());
            if (currentUser.getDescription() != null) {
                userDescription.setText(currentUser.getDescription());
            }
        });

        logoutButton.setOnClickListener(view -> new AuthenticationServiceImpl().logout(getApplicationContext()));

        // Return to timeline from settings menu
        returnButton = findViewById(R.id.settings_return);
        returnButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), TimelineActivity.class)));

        // Go to your own profile from the settings menu
        profileButton = findViewById(R.id.settings_profile);
        profileButton.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ProfileActivity.class)));
    }

    protected FirebaseUser getLoggedInUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
            startActivity(new Intent(this, LoginActivity.class));
        return firebaseUser;
    }

    public void editTextView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewSwitcher) {
            ViewSwitcher switcher = (ViewSwitcher) parent;
            switch (view.getId()) {
                case R.id.user_name:
                    nameText.setText(userName.getText());
                    usernameSubmit = findViewById(R.id.edit_name_submit);
                    usernameSubmit.setVisibility(View.VISIBLE); //Show the "GO" button
                    break;
                case R.id.user_handle:
                    handleText.setText(userHandle.getText());
                    handleSubmit = findViewById(R.id.edit_handle_submit);
                    handleSubmit.setVisibility(View.VISIBLE);
                    break;
                case R.id.user_description:
                    descriptionText.setText(userDescription.getText());
                    descriptionSubmit = findViewById(R.id.edit_description_submit);
                    descriptionSubmit.setVisibility(View.VISIBLE);
                    break;
            }
            switcher.showNext();
        }
    }

    public void submitEditUser(View view) {
        String editText;
        HashMap<String, Object> attributes = new HashMap<>();
        switch (view.getId()) { //TODO: This could probably be cleaner
            case R.id.edit_name_submit:
                editText = nameText.getText().toString();
                if (editText.length() < 1) {
                    Toast.makeText(this, "Display name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                userName.setText(editText);
                currentUser.setDisplayName(editText);
                attributes.put("displayName", editText);
                break;
            case R.id.edit_handle_submit:
                editText = handleText.getText().toString();
                if (editText.length() < 1) {
                    Toast.makeText(this, "Handle cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                userHandle.setText(editText);
                currentUser.setHandle(editText);
                attributes.put("handle", editText);
                break;
            case R.id.edit_description_submit:
                editText = descriptionText.getText().toString();
                userDescription.setText(editText);
                currentUser.setDescription(editText);
                attributes.put("description", editText);
                break;
        }
        Task<Void> update = new UserDao().update(currentUser.getId(), attributes);
        Toast.makeText(this, update.isSuccessful() ? "Update successful" : "Update unsuccessful", Toast.LENGTH_SHORT);


        ViewParent parent = view.getParent();
        if (parent != null && parent.getParent() instanceof ViewSwitcher) {
            ((ViewSwitcher) parent.getParent()).showPrevious();
        }
    }
}