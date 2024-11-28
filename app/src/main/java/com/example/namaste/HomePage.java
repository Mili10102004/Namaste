package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private Button checkPricesButton;
    private Button checkTimeButton;
    private ImageButton profileButton;
    private ImageButton helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);  // Inflates the layout

        // Initialize the views
        checkPricesButton = findViewById(R.id.checkPricesButton);
        checkTimeButton = findViewById(R.id.checkTimeButton);
        profileButton = findViewById(R.id.profile);
        helpButton = findViewById(R.id.help);

        // Set up button click listeners

        checkPricesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Shipping Calculator Activity
                Intent intent = new Intent(HomePage.this, ShippingPageActivity.class);
                startActivity(intent);
            }
        });

        checkTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Delivery Time Estimator Activity
                Intent intent = new Intent(HomePage.this, DeliveryTimeEstimationActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Profile Activity
                Intent intent = new Intent(HomePage.this, ProfileSettingActivity.class);
                startActivity(intent);
            }
        });



        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Help & Support Activity
                Intent intent = new Intent(HomePage.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}
