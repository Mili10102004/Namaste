package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewOrderActivity extends AppCompatActivity {
    private Button bulkOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        bulkOrderButton = findViewById(R.id.bulkOrderButton);

        // Creating the layout for two sections
        LinearLayout layout = findViewById(R.id.mainLayout);
        layout.setOrientation(LinearLayout.VERTICAL);


        bulkOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Shipping Calculator Activity
                Intent intent = new Intent(NewOrderActivity.this, BulkShippingActivity.class);
                startActivity(intent);
            }
        });
        // Individual Order Section
        TextView individualOrder = new TextView(this);
        // individualOrder.setText("Individual Order");
        individualOrder.setTextSize(18);
        individualOrder.setPadding(16, 16, 16, 16);
        individualOrder.setBackgroundResource(android.R.color.darker_gray);
        layout.addView(individualOrder);

        // Bulk Order Section
        TextView bulkOrder = new TextView(this);
        bulkOrder.setText("Bulk Order");
        bulkOrder.setTextSize(18);
        bulkOrder.setPadding(16, 16, 16, 16);
        bulkOrder.setBackgroundResource(android.R.color.darker_gray);
        layout.addView(bulkOrder);
    }
}
