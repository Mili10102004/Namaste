package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

public class OrderConfirmation extends AppCompatActivity {

    // Declare UI components
    private TextView orderTotalAmount, paymentStatusMessage;
    private Button continueShoppingButton;
    private ImageView orderConfirmationGif;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Link UI components
        orderTotalAmount = findViewById(R.id.order_total_amount);
        paymentStatusMessage = findViewById(R.id.payment_status_message);
        continueShoppingButton = findViewById(R.id.continue_shopping_button);
        orderConfirmationGif = findViewById(R.id.order_confirmation_gif);

        // Retrieve the order amount and display it dynamically
        String orderAmount = getIntent().getStringExtra("ORDER_AMOUNT");
        if (orderAmount != null) {
            orderTotalAmount.setText(orderAmount);
        } else {
            orderTotalAmount.setText("$0.00"); // Default if no amount passed
        }

        // Set payment status message
        paymentStatusMessage.setText("Your payment has been successfully completed.\nPlease check the delivery status at the Order Tracking page.");

        // Handle continue shopping button click
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store order status in the database
                storeOrderStatus();

                // Redirect to Home page after clicking 'Continue Shopping'
                Intent intent = new Intent(OrderConfirmation.this, HomePage.class);
                startActivity(intent);
                finish(); // Close this activity to prevent user from going back to the confirmation page
            }
        });
    }

    // Method to store order status in Firestore database
    private void storeOrderStatus() {
        // Create a new order status document
        DocumentReference orderRef = db.collection("orders").document();

        // Assuming you have order details such as user ID, status, and amount
        String orderStatus = "Completed"; // You can update this based on the actual order status logic
        String userId = "sample_user_id"; // Replace with actual user ID
        String orderAmount = orderTotalAmount.getText().toString();

        // Create an order status object to store in Firestore
        OrderStatus orderStatusObj = new OrderStatus(userId, orderAmount, orderStatus);

        // Save the order status in Firestore
        orderRef.set(orderStatusObj)
                .addOnSuccessListener(aVoid -> {
                    // Toast message for success
                    Toast.makeText(OrderConfirmation.this, "Order status saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Toast message for failure
                    Toast.makeText(OrderConfirmation.this, "Failed to save order status", Toast.LENGTH_SHORT).show();
                });
    }

    // OrderStatus model class
    public static class OrderStatus {
        private String userId;
        private String orderAmount;
        private String orderStatus;

        // Constructor
        public OrderStatus(String userId, String orderAmount, String orderStatus) {
            this.userId = userId;
            this.orderAmount = orderAmount;
            this.orderStatus = orderStatus;
        }

        // Getters and setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
