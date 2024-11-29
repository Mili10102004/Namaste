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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentPage extends AppCompatActivity implements PaymentResultListener {

    private RadioGroup courierGroup, paymentGroup;
    private RadioButton selectedCourier, selectedPayment;
    private Button confirmOrderButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        // Initialize Firebase Realtime Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("orders");

        // Initialize Views
        courierGroup = findViewById(R.id.courier_options_group);
        paymentGroup = findViewById(R.id.payment_methods_group);
        confirmOrderButton = findViewById(R.id.confirm_order_button);

        // Razorpay Checkout SDK initialization
        Checkout.preload(getApplicationContext());

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedCourierId = courierGroup.getCheckedRadioButtonId();
                int selectedPaymentId = paymentGroup.getCheckedRadioButtonId();

                if (selectedCourierId == -1 || selectedPaymentId == -1) {
                    Toast.makeText(PaymentPage.this, "Please select both courier and payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                selectedCourier = findViewById(selectedCourierId);
                selectedPayment = findViewById(selectedPaymentId);

                String courierService = selectedCourier.getText().toString();
                String paymentMethod = selectedPayment.getText().toString();

                String orderId = databaseReference.push().getKey();
                OrderDetails orderDetails = new OrderDetails(courierService, paymentMethod);
                databaseReference.child(orderId).setValue(orderDetails);

                startPayment();
            }
        });
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_vZOeFkvTiUS76f");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Namaste Now");
            options.put("description", "Order Payment");
            options.put("currency", "INR");
            options.put("amount", "50000");

            JSONObject prefill = new JSONObject();
            prefill.put("email", "user@example.com");
            prefill.put("contact", "9876543210");
            options.put("prefill", prefill);

            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PaymentPage.this, OrderConfirmation.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_SHORT).show();
    }

    public static class OrderDetails {
        public String courierService;
        public String paymentMethod;

        public OrderDetails() { }

        public OrderDetails(String courierService, String paymentMethod) {
            this.courierService = courierService;
            this.paymentMethod = paymentMethod;
        }
    }
}
