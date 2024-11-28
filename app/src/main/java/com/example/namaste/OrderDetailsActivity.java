package com.example.namaste; // Replace with your package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView textOrderDate, orderStatus, textPackageDetails, textReceiptDetails, helpText;
    private Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Initialize UI elements
        textOrderDate = findViewById(R.id.textOrderDate);
        orderStatus = findViewById(R.id.orderStatus);
        textPackageDetails = findViewById(R.id.textPackageDetails);
        textReceiptDetails = findViewById(R.id.textReceiptDetails);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        helpText = findViewById(R.id.helpText); // Help TextView

        // Set dynamic data (mock example)
        textOrderDate.setText("Order date: Mar 15, 1:09 PM");
        orderStatus.setText("Order in progress");
        textPackageDetails.setText("Medium Package: x1\nLaptops, Mobiles, and Other Gadgets");
        textReceiptDetails.setText("Total Amount: ₹500\nPayment Mode: Online (Paid)");

        // Handle Back button to go to Home page (MainActivity)
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the home page (MainActivity)
                Intent homeIntent = new Intent(OrderDetailsActivity.this, PackageDetailsActivity.class);
                startActivity(homeIntent);
                finish();  // Optionally finish the current activity to prevent back navigation
            }
        });

        // Handle Next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(OrderDetailsActivity.this,HomePage.class);
                startActivity(intent);
            }
        });

        // Handle Help Text click to redirect to Help page
        helpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Help page
                Intent helpIntent = new Intent(OrderDetailsActivity.this, HelpActivity.class);
                startActivity(helpIntent);
            }
        });
    }
}
