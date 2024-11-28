package com.example.namaste;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendPackageActivity extends AppCompatActivity {

    private EditText pickupAddress, deliveryAddress, packageDetails;
    private CheckBox specialInstructions;
    private TextView deliveryCharges, amountPayable;
    private Button scheduleButton, pickUpNowButton;
    private FirebaseDatabase database;
    private DatabaseReference shipmentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_package);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        shipmentRef = database.getReference("shipments");

        // Bind views
        pickupAddress = findViewById(R.id.pickup_address);
        deliveryAddress = findViewById(R.id.delivery_address);
        packageDetails = findViewById(R.id.package_details);
        specialInstructions = findViewById(R.id.special_instructions);
        deliveryCharges = findViewById(R.id.delivery_charges);
        amountPayable = findViewById(R.id.amount_payable);
        scheduleButton = findViewById(R.id.schedule_button);
        pickUpNowButton = findViewById(R.id.pickup_now_button);

        // Schedule button click listener
        scheduleButton.setOnClickListener(v -> schedulePackage());

        // Pick up now button click listener
        pickUpNowButton.setOnClickListener(v -> pickUpPackage());
    }

    private void schedulePackage() {
        // Get the values from the input fields
        String pickup = pickupAddress.getText().toString().trim();
        String delivery = deliveryAddress.getText().toString().trim();
        String packageSize = packageDetails.getText().toString().trim();
        String specialInstruction = specialInstructions.isChecked() ? "Yes" : "No";

        if (pickup.isEmpty() || delivery.isEmpty() || packageSize.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
        } else {
            // Save package data to Firebase
            String shipmentId = shipmentRef.push().getKey();
            Shipment shipment = new Shipment(pickup, delivery, packageSize, specialInstruction);
            shipmentRef.child(shipmentId).setValue(shipment);

            // Show success message
            Toast.makeText(this, "Package scheduled successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickUpPackage() {
        String pickup = pickupAddress.getText().toString().trim();
        String delivery = deliveryAddress.getText().toString().trim();

        if (pickup.isEmpty() || delivery.isEmpty()) {
            Toast.makeText(this, "Please fill in both pickup and delivery addresses", Toast.LENGTH_SHORT).show();
        } else {
            // Simulate pickup now action
            Toast.makeText(this, "Pickup has been requested.", Toast.LENGTH_SHORT).show();
        }
    }

    // Shipment class to model package data
    public static class Shipment {
        public String pickupAddress;
        public String deliveryAddress;
        public String packageSize;
        public String specialInstructions;

        public Shipment(String pickupAddress, String deliveryAddress, String packageSize, String specialInstructions) {
            this.pickupAddress = pickupAddress;
            this.deliveryAddress = deliveryAddress;
            this.packageSize = packageSize;
            this.specialInstructions = specialInstructions;
        }
    }
}
