package com.example.namaste;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NotificationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page); // Link to the XML layout file

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Check if the toolbar is found in the layout
        if (toolbar != null) {
            setSupportActionBar(toolbar); // Set the toolbar as the action bar

            // Enable the back button in the toolbar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back arrow

                // Customize the size of the back arrow
                Drawable backArrow = getResources().getDrawable(R.drawable.notification);
                backArrow.setBounds(0, 0, 80, 80);  // Adjust size to fit the design
                getSupportActionBar().setHomeAsUpIndicator(backArrow); // Set the custom-sized back arrow
            }
        } else {
            // Log or handle if the toolbar is not found in the layout
            // Example: Log.e("NotificationsPage", "Toolbar not found in the layout");
        }
    }

    // Handle back button click in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button press in the toolbar
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
