package com.example.namaste; // Change to your actual package name

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSettingActivity extends AppCompatActivity {

    // Declare UI components
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button addAddressButton;
    private Button editVisaButton;
    private Button editMastercardButton;
    private Button editPayPalButton;
    private Button viewOrderDetailsButton1;
    private Button viewOrderDetailsButton2;
    private Button referralDetailsButton;
    private Switch promotionalSwitch;
    private Switch transactionalSwitch;
    private CheckBox allowTrackingCheckBox;
    private CheckBox showProfileCheckBox;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting); // Ensure your XML file is named activity_profile_settings.xml

        // Initialize UI components
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        addAddressButton = findViewById(R.id.add_new_address_button);
        editVisaButton = findViewById(R.id.edit_visa_button);
        editMastercardButton = findViewById(R.id.edit_mastercard_button);
        editPayPalButton = findViewById(R.id.edit_paypal_button);
        viewOrderDetailsButton1 = findViewById(R.id.view_order_details_button_1);
        viewOrderDetailsButton2 = findViewById(R.id.view_order_details_button_2);
        referralDetailsButton = findViewById(R.id.referral_details_button);
        promotionalSwitch = findViewById(R.id.promotional_switch);
        transactionalSwitch = findViewById(R.id.transactional_switch);
        allowTrackingCheckBox = findViewById(R.id.allow_tracking_checkbox);
        showProfileCheckBox = findViewById(R.id.show_profile_checkbox);
        backButton = findViewById(R.id.back_button);

        // Set onClick listeners
        addAddressButton.setOnClickListener(v -> {
            // Logic to add a new address
        });

        editVisaButton.setOnClickListener(v -> {
            // Logic to edit Visa card details
        });

        editMastercardButton.setOnClickListener(v -> {
            // Logic to edit Mastercard details
        });

        editPayPalButton.setOnClickListener(v -> {
            // Logic to edit PayPal details
        });

        viewOrderDetailsButton1.setOnClickListener(v -> {
            // Logic to view order details for Order #1234
        });

        viewOrderDetailsButton2.setOnClickListener(v -> {
            // Logic to view order details for Order #5678
        });

        referralDetailsButton.setOnClickListener(v -> {
            // Logic to view referral details
        });

        promotionalSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Logic to handle promotional notifications toggle
        });

        transactionalSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Logic to handle transactional notifications toggle
        });

        allowTrackingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Logic to handle allow tracking checkbox
        });

        showProfileCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Logic to handle show profile checkbox
        });

        backButton.setOnClickListener(v -> {
            // Logic to handle back button click
            finish(); // This will close the current activity and return to the previous one
        });
    }
}
