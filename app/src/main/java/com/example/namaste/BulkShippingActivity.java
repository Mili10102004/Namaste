package com.example.namaste;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class BulkShippingActivity extends AppCompatActivity {

    private EditText pickupCity, addressPickup1, addressPickup2, destinationCity, addressDestination1, addressDestination2;
    private EditText recipientName, weight, dimensions, itemType;
    private Button confirmShip;

    // Firebase Database Reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_shipping);

        // Initialize Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://namaste-e16fa-default-rtdb.firebaseio.com/");

        // Link UI components
        pickupCity = findViewById(R.id.pickup_city);
        addressPickup1 = findViewById(R.id.address_pickup1);
        addressPickup2 = findViewById(R.id.address_pickup2);
        destinationCity = findViewById(R.id.destination_city);
        addressDestination1 = findViewById(R.id.address_destination1);
        addressDestination2 = findViewById(R.id.address_destination2);
        recipientName = findViewById(R.id.recipient_name);
        weight = findViewById(R.id.weight);
        dimensions = findViewById(R.id.dimensions);
        itemType = findViewById(R.id.item_type);
        confirmShip = findViewById(R.id.confirm_ship);

        // Set up button listener
        confirmShip.setOnClickListener(v -> submitBooking());
    }

    private void submitBooking() {
        // Get values from fields
        String pickupCityText = pickupCity.getText().toString().trim();
        String addressPickup1Text = addressPickup1.getText().toString().trim();
        String addressPickup2Text = addressPickup2.getText().toString().trim();
        String destinationCityText = destinationCity.getText().toString().trim();
        String addressDestination1Text = addressDestination1.getText().toString().trim();
        String addressDestination2Text = addressDestination2.getText().toString().trim();
        String recipientNameText = recipientName.getText().toString().trim();
        String weightText = weight.getText().toString().trim();
        String dimensionsText = dimensions.getText().toString().trim();
        String itemTypeText = itemType.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(pickupCityText) || TextUtils.isEmpty(destinationCityText) || TextUtils.isEmpty(recipientNameText)) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique booking ID
        String bookingId = UUID.randomUUID().toString();

        // Prepare data
        HashMap<String, Object> bookingData = new HashMap<>();
        bookingData.put("pickupCity", pickupCityText);
        bookingData.put("addressPickup1", addressPickup1Text);
        bookingData.put("addressPickup2", addressPickup2Text);
        bookingData.put("destinationCity", destinationCityText);
        bookingData.put("addressDestination1", addressDestination1Text);
        bookingData.put("addressDestination2", addressDestination2Text);
        bookingData.put("recipientName", recipientNameText);
        bookingData.put("weight", weightText);
        bookingData.put("dimensions", dimensionsText);
        bookingData.put("itemType", itemTypeText);
        bookingData.put("bookingId", bookingId);

        // Save data to Firebase
        databaseReference.child(recipientNameText).child(bookingId).setValue(bookingData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(this, "Failed to book. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        pickupCity.setText("");
        addressPickup1.setText("");
        addressPickup2.setText("");
        destinationCity.setText("");
        addressDestination1.setText("");
        addressDestination2.setText("");
        recipientName.setText("");
        weight.setText("");
        dimensions.setText("");
        itemType.setText("");
    }
}
