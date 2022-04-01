package com.example.comp4200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class SettingsActivity extends AppCompatActivity {
    TextView userName;
    TextView userHandle;
    TextView userDescription;
    EditText nameText;
    EditText handleText;
    EditText descriptionText;
    ImageButton imageButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras(); //TODO: Or use shared preferences user
        userName = findViewById(R.id.user_name);
        userHandle = findViewById(R.id.user_handle);
        userDescription = findViewById(R.id.user_description);
        nameText = findViewById(R.id.edit_name);
        handleText = findViewById(R.id.edit_handle);
        descriptionText = findViewById(R.id.edit_description);
        imageButton = findViewById(R.id.imageButton);
        logoutButton = findViewById(R.id.logout_button);
    }

    public void editTextView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewSwitcher) {
            ViewSwitcher switcher = (ViewSwitcher) parent;
            switch (view.getId()) {
                case R.id.user_name:
                    nameText.setText(userName.getText());
                    break;
                case R.id.user_handle:
                    handleText.setText(userHandle.getText());
                    break;
                case R.id.user_description:
                    descriptionText.setText(userDescription.getText());
                    break;
            }
            switcher.showNext();
        }
    }

    public void submitEditUser(View view) {
        String editText;
        switch (view.getId()) { //TODO: Save to database
            case R.id.edit_name_submit:
                editText = nameText.getText().toString();
                userName.setText(editText);
                break;
            case R.id.edit_handle_submit:
                editText = handleText.getText().toString();
                userHandle.setText(editText);
                break;
            case R.id.edit_description_submit:
                editText = descriptionText.getText().toString();
                userDescription.setText(editText);
                break;
        }
        //TODO: Display toast based on firebase output
        Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT);

        ViewParent parent = view.getParent();
        if (parent != null && parent.getParent() instanceof ViewSwitcher) {
            ((ViewSwitcher) parent.getParent()).showPrevious();
        }
    }
}