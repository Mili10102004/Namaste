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

    private RadioGroup courierGroup, paymentGroup;
    private RadioButton selectedCourier, selectedPayment;
    private Button confirmOrderButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);  // Ensure this matches your XML layout file name

        // Initialize Firebase Realtime Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("orders");  // "orders" is the node where data will be stored

        // Initialize Views
        courierGroup = findViewById(R.id.courier_options_group);
        paymentGroup = findViewById(R.id.payment_methods_group);
        confirmOrderButton = findViewById(R.id.confirm_order_button);

        // Button click listener to confirm the order
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected courier and payment method
                int selectedCourierId = courierGroup.getCheckedRadioButtonId();
                int selectedPaymentId = paymentGroup.getCheckedRadioButtonId();

                // Ensure that both options are selected
                if (selectedCourierId == -1 || selectedPaymentId == -1) {
                    Toast.makeText(PaymentPage.this, "Please select both courier and payment method", Toast.LENGTH_SHORT).show();
                    return;  // Exit if not all options are selected
                }

                selectedCourier = findViewById(selectedCourierId);
                selectedPayment = findViewById(selectedPaymentId);

                // Get the selected values
                String courierService = selectedCourier.getText().toString();
                String paymentMethod = selectedPayment.getText().toString();

                // Store selected data in Firebase
                String orderId = databaseReference.push().getKey();  // Generate unique order ID
                OrderDetails orderDetails = new OrderDetails(courierService, paymentMethod);
                databaseReference.child(orderId).setValue(orderDetails);

                // Redirect to OrderConfirmation page
                Intent intent = new Intent(PaymentPage.this, OrderConfirmation.class);
                startActivity(intent);
            }
        });
    }

    // Class to store order details
    public static class OrderDetails {
        public String courierService;
        public String paymentMethod;

        public OrderDetails() {
            // Default constructor required for calls to DataSnapshot.getValue(OrderDetails.class)
        }

        public OrderDetails(String courierService, String paymentMethod) {
            this.courierService = courierService;
            this.paymentMethod = paymentMethod;
        }
    }
}
