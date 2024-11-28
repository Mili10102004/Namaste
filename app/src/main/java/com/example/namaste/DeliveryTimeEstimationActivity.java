package com.example.namaste;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeliveryTimeEstimationActivity extends AppCompatActivity {

    private Spinner countrySpinner;
    private RadioGroup packageTypeGroup;
    private EditText weightInput;
    private Button getDeliveryTimeButton;
    private TextView estimatedDeliveryTimeText, shippingCostText;
    private View deliveryEstimateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_time_estimation);

        // Initializing views
        countrySpinner = findViewById(R.id.countrySpinner);
        packageTypeGroup = findViewById(R.id.packageTypeGroup);
        weightInput = findViewById(R.id.weightInput);
        getDeliveryTimeButton = findViewById(R.id.getDeliveryTimeButton);
        estimatedDeliveryTimeText = findViewById(R.id.estimatedDeliveryTimeText);
        shippingCostText = findViewById(R.id.shippingCostText);
        deliveryEstimateResult = findViewById(R.id.deliveryEstimateResult);

        // Button click listener for getting the delivery estimate
        getDeliveryTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDeliveryTimeEstimate();
            }
        });
    }

    private void calculateDeliveryTimeEstimate() {
        // Get destination country
        String selectedCountry = countrySpinner.getSelectedItem().toString();

        // Get package type (Non-Document, Document, or Temperature Controlled)
        int selectedPackageTypeId = packageTypeGroup.getCheckedRadioButtonId();
        RadioButton selectedPackageType = findViewById(selectedPackageTypeId);
        String packageType = selectedPackageType.getText().toString();

        // Get weight input
        String weightText = weightInput.getText().toString();
        if (weightText.isEmpty()) {
            Toast.makeText(DeliveryTimeEstimationActivity.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
            return;
        }
        double weight = Double.parseDouble(weightText);

        // Calculate estimated delivery time and shipping cost
        String estimatedTime = calculateEstimatedDeliveryTime(selectedCountry, packageType, weight);
        double shippingCost = calculateShippingCost(weight);

        // Show the results
        showResults(estimatedTime, shippingCost);
    }

    private String calculateEstimatedDeliveryTime(String country, String packageType, double weight) {
        // Simple estimation based on package type and weight
        if (packageType.equals("Temperature Controlled")) {
            return "7-10 Days";
        } else if (weight > 5) {
            return "5-7 Days";
        }
        else {
            return "3-5 Days";
        }
    }

    private double calculateShippingCost(double weight) {
        // Simple cost calculation based on weight
        if (weight <= 1) {
            return 500; // ₹500 for packages up to 1kg
        } else if (weight <= 5) {
            return 1000; // ₹1000 for packages 1kg to 5kg
        } else {
            return 1500; // ₹1500 for packages above 5kg
        }
    }

    private void showResults(String estimatedTime, double shippingCost) {
        // Set the estimated delivery time and shipping cost
        estimatedDeliveryTimeText.setText("Estimated Delivery: " + estimatedTime);
        shippingCostText.setText("Shipping Cost: ₹" + shippingCost);

        // Show the result card
        deliveryEstimateResult.setVisibility(View.VISIBLE);
    }
}
