package com.example.namaste;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class BulkShippingActivity extends AppCompatActivity {

    private EditText pickupCity, addressPickup1, addressPickup2, destinationCity;
    private EditText receiverName, receiverAddress, receiverLandmark, receiverPhone;
    private RadioGroup radioGroupSize;
    private CheckBox checkBooks, checkClothing, checkElectronics, checkJewellery;
    private Button confirmShip;

    // Firebase Database Reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_shipping);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://namaste-e16fa-default-rtdb.firebaseio.com/gzhhz");

        // Link UI components
        pickupCity = findViewById(R.id.pickup_city);
        addressPickup1 = findViewById(R.id.address_pickup1);
        addressPickup2 = findViewById(R.id.address_pickup2);
        destinationCity = findViewById(R.id.destination_city);
        receiverName = findViewById(R.id.receiver_detail);
        receiverAddress = findViewById(R.id.receiver_address);
        receiverLandmark = findViewById(R.id.receiver_landmark);
        receiverPhone = findViewById(R.id.receiver_phone);
        radioGroupSize = findViewById(R.id.radioGroupSize);
        checkBooks = findViewById(R.id.checkBooks);
        checkClothing = findViewById(R.id.checkClothing);
        checkElectronics = findViewById(R.id.checkElectronics);
        checkJewellery = findViewById(R.id.checkJewellery);
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
        String receiverNameText = receiverName.getText().toString().trim();
        String receiverAddressText = receiverAddress.getText().toString().trim();
        String receiverLandmarkText = receiverLandmark.getText().toString().trim();
        String receiverPhoneText = receiverPhone.getText().toString().trim();

        // Get selected size
        int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();
        String selectedSize = "";
        if (selectedSizeId != -1) {
            RadioButton selectedSizeButton = findViewById(selectedSizeId);
            selectedSize = selectedSizeButton.getText().toString();
        }

        // Get selected categories
        StringBuilder categories = new StringBuilder();
        if (checkBooks.isChecked()) categories.append("Books, Documents, ");
        if (checkClothing.isChecked()) categories.append("Clothing, Accessories, ");
        if (checkElectronics.isChecked()) categories.append("Laptop, Mobiles, ");
        if (checkJewellery.isChecked()) categories.append("Other valuables, ");
        if (categories.length() > 0) categories.setLength(categories.length() - 2); // Remove trailing comma

        // Validate inputs
        if (TextUtils.isEmpty(pickupCityText) || TextUtils.isEmpty(destinationCityText) ||
                TextUtils.isEmpty(receiverNameText) || TextUtils.isEmpty(receiverPhoneText)) {
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
        bookingData.put("receiverName", receiverNameText);
        bookingData.put("receiverAddress", receiverAddressText);
        bookingData.put("receiverLandmark", receiverLandmarkText);
        bookingData.put("receiverPhone", receiverPhoneText);
        bookingData.put("parcelSize", selectedSize);
        bookingData.put("categories", categories.toString());
        bookingData.put("bookingId", bookingId);

        // Save data to Firebase
        databaseReference.child("BulkBookings").child(bookingId).setValue(bookingData)
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
        receiverName.setText("");
        receiverAddress.setText("");
        receiverLandmark.setText("");
        receiverPhone.setText("");
        radioGroupSize.clearCheck();
        checkBooks.setChecked(false);
        checkClothing.setChecked(false);
        checkElectronics.setChecked(false);
        checkJewellery.setChecked(false);
    }
}
