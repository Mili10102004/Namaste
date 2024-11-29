package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private Button checkPricesButton;
    private ImageButton profileButton;
    private ImageButton helpButton;
    private ImageButton notificationButton;  // Add the notification button variable
    private Button startSendingPackageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);  // Inflates the layout

        // Initialize the views
        checkPricesButton = findViewById(R.id.checkPricesButton);
        profileButton = findViewById(R.id.profile);
        helpButton = findViewById(R.id.help);
        notificationButton = findViewById(R.id.notification);
        startSendingPackageButton = findViewById(R.id.startSendingPackageButton);// Initialize the notification button

        // Set up button click listeners
        checkPricesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Shipping Calculator Activity
                Intent intent = new Intent(HomePage.this, ShippingPageActivity.class);
                startActivity(intent);
            }
        });


        startSendingPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to New Order Activity
                Intent intent = new Intent(HomePage.this, NewOrderActivity.class);
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

        // Set up the notification button click listener
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to NotificationPage Activity
                Intent intent = new Intent(HomePage.this, NotificationPage.class);
                startActivity(intent);
            }
        });
    }
}