package com.example.namaste;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        // Creating the layout for two sections
        LinearLayout layout = findViewById(R.id.mainLayout);
        layout.setOrientation(LinearLayout.VERTICAL);

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
