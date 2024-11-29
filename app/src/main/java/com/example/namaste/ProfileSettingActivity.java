package com.example.namaste;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSettingActivity extends AppCompatActivity {

    private Switch pushNotificationSwitch;
    private Spinner languageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        // Initialize UI elements
        pushNotificationSwitch = findViewById(R.id.pushNotificationSwitch);
        languageSpinner = findViewById(R.id.languageSpinner);

        // Set up the language spinner with options
        setupLanguageSpinner();

        // Set the default state of the switch based on the current preference
        pushNotificationSwitch.setChecked(true);  // Assuming notifications are enabled by default

        // Set the listener for the push notification toggle
        pushNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Enable push notifications
                Toast.makeText(ProfileSettingActivity.this, "Push Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                // Disable push notifications
                Toast.makeText(ProfileSettingActivity.this, "Push Notifications Disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupLanguageSpinner() {
        // Create an array of languages
        String[] languages = {"English", "Hindi", "Spanish", "French", "German"};

        // Create an ArrayAdapter with the language options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        languageSpinner.setAdapter(adapter);

        // Optionally, set a listener for language selection
        languageSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLanguage = parentView.getItemAtPosition(position).toString();
                Toast.makeText(ProfileSettingActivity.this, "Selected Language: " + selectedLanguage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parentView) {
                // Do nothing if no language is selected
            }
        });
    }
}
