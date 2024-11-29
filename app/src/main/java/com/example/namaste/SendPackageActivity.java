package com.example.namaste;

import android.app.ProgressDialog;
import android.content.Intent;
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

    private EditText senderName, senderAddress, senderLandmark, senderPhone;
    private EditText receiverName, receiverAddress, receiverLandmark, receiverPhone;
    private CheckBox specialInstructions;
    private TextView selectedItemsText;
    private Button scheduleButton;

    private DatabaseReference shipmentRef;
    private String selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_package);

        // Configure Firebase with the provided database URL
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://namaste-e16fa-default-rtdb.firebaseio.com/");
        shipmentRef = database.getReference("shipments");

        // Bind views from XML layout
        senderName = findViewById(R.id.sender_name);
        senderAddress = findViewById(R.id.sender_address);
        senderLandmark = findViewById(R.id.sender_landmark);
        senderPhone = findViewById(R.id.sender_number);

        receiverName = findViewById(R.id.receiver_name);
        receiverAddress = findViewById(R.id.receiver_address);
        receiverLandmark = findViewById(R.id.receiver_landmark);
        receiverPhone = findViewById(R.id.receiver_phone);

        specialInstructions = findViewById(R.id.special_instructions);
        selectedItemsText = findViewById(R.id.selected_items_text);
        scheduleButton = findViewById(R.id.schedule_button);

        // Retrieve selected items from Intent extras
        selectedItems = getIntent().getStringExtra("selectedItems");
        if (selectedItems == null || selectedItems.isEmpty()) {
            selectedItems = "No items selected.";
        }
        selectedItemsText.setText(selectedItems);

        // Set up the Schedule button's click listener
        scheduleButton.setOnClickListener(v -> {
            if (schedulePackage()) { // Schedule package in Firebase
                Intent intent = new Intent(SendPackageActivity.this, PaymentPage.class);
                startActivity(intent); // Redirect to PaymentPage
            }
        });
    }

    private boolean schedulePackage() {
        // Validate input fields
        if (!validateInputFields()) {
            return false;
        }

        // Generate a unique ID for the shipment
        String shipmentId = shipmentRef.push().getKey();
        if (shipmentId == null) {
            Toast.makeText(this, "Error generating shipment ID. Please try again.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Create a Shipment object with input data
        Shipment shipment = new Shipment(
                senderName.getText().toString().trim(),
                senderAddress.getText().toString().trim() + " " + senderLandmark.getText().toString().trim(),
                senderPhone.getText().toString().trim(),
                receiverName.getText().toString().trim(),
                receiverAddress.getText().toString().trim() + " " + receiverLandmark.getText().toString().trim(),
                receiverPhone.getText().toString().trim(),
                specialInstructions.isChecked() ? "Yes" : "No",
                selectedItems
        );

        // Show a loading dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Scheduling package...");
        progressDialog.show();

        // Save the shipment object to Firebase
        shipmentRef.child(shipmentId).setValue(shipment).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(this, "Package scheduled successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after successful scheduling
            } else {
                Toast.makeText(this, "Failed to schedule package. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    private boolean validateInputFields() {
        // Check if any required fields are empty
        if (senderName.getText().toString().trim().isEmpty() ||
                senderAddress.getText().toString().trim().isEmpty() ||
                senderPhone.getText().toString().trim().isEmpty() ||
                receiverName.getText().toString().trim().isEmpty() ||
                receiverAddress.getText().toString().trim().isEmpty() ||
                receiverPhone.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate phone numbers
        if (!senderPhone.getText().toString().matches("\\d{10}") ||
                !receiverPhone.getText().toString().matches("\\d{10}")) {
            Toast.makeText(this, "Enter a valid 10-digit phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Inner Shipment class to model package data
    public static class Shipment {
        public String senderName;
        public String senderAddress;
        public String senderPhone;
        public String receiverName;
        public String receiverAddress;
        public String receiverPhone;
        public String specialInstructions;
        public String selectedItems;

        // Default constructor required for Firebase
        public Shipment() {
        }

        public Shipment(String senderName, String senderAddress, String senderPhone,
                        String receiverName, String receiverAddress, String receiverPhone,
                        String specialInstructions, String selectedItems) {
            this.senderName = senderName;
            this.senderAddress = senderAddress;
            this.senderPhone = senderPhone;
            this.receiverName = receiverName;
            this.receiverAddress = receiverAddress;
            this.receiverPhone = receiverPhone;
            this.specialInstructions = specialInstructions;
            this.selectedItems = selectedItems;
        }
    }
}
