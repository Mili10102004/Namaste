package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentPage extends AppCompatActivity {

    private DatabaseReference database;

    // UI Elements
    private RadioGroup courierGroup, paymentGroup;
    private Button confirmOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);  // Replace with your XML layout name

        // Initialize Realtime Database
        database = FirebaseDatabase.getInstance().getReference("orders");  // Use "orders" as the node

        // Initialize UI elements
        courierGroup = findViewById(R.id.courier_options_group);
        paymentGroup = findViewById(R.id.payment_methods_group);
        confirmOrderButton = findViewById(R.id.confirm_order_button);

        // Confirm Order Button Click Listener
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePaymentDetails();
            }
        });
    }

    // Method to save payment details to Firebase Realtime Database
    private void savePaymentDetails() {
        // Get the selected courier option
        int selectedCourierId = courierGroup.getCheckedRadioButtonId();
        RadioButton selectedCourierButton = findViewById(selectedCourierId);
        String selectedCourier = selectedCourierButton != null ? selectedCourierButton.getText().toString() : "Not Selected";

        // Get the selected payment method
        int selectedPaymentId = paymentGroup.getCheckedRadioButtonId();
        RadioButton selectedPaymentButton = findViewById(selectedPaymentId);
        String selectedPaymentMethod = selectedPaymentButton != null ? selectedPaymentButton.getText().toString() : "Not Selected";

        // Create a Payment object to store the data
        Payment payment = new Payment(selectedCourier, selectedPaymentMethod, "Order Confirmed");

        // Save the payment data to Firebase Realtime Database under the "orders" node
        String orderId = database.push().getKey();  // Generate a unique key for the order
        if (orderId != null) {
            database.child(orderId).setValue(payment)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(PaymentPage.this, "Payment details saved successfully!", Toast.LENGTH_SHORT).show();

                        // Redirect to confirmation page after saving data
                        Intent intent = new Intent(PaymentPage.this, OrderConfirmation.class);
                        startActivity(intent);
                        finish(); // Close this activity so the user can't navigate back
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PaymentPage.this, "Error saving payment details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Data model class for Payment
    public static class Payment {
        private String courier;
        private String paymentMethod;
        private String orderStatus;

        public Payment(String courier, String paymentMethod, String orderStatus) {
            this.courier = courier;
            this.paymentMethod = paymentMethod;
            this.orderStatus = orderStatus;
        }

        public String getCourier() {
            return courier;
        }

        public void setCourier(String courier) {
            this.courier = courier;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
