package com.example.namaste;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class ShippingPageActivity extends AppCompatActivity {

    private Spinner countrySpinner;
    private RadioGroup packageTypeGroup;
    private EditText weightInput;
    private RadioGroup weightUnitGroup;
    private Button minusButton, plusButton, getEstimateButton;
    private CheckBox volumetricWeightCheckbox;
    private TextView basicEstimateTitle, originalPrice, discountPrice, estimatedDeliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_page);

        // Initializing the views
        countrySpinner = findViewById(R.id.countrySpinner);
        packageTypeGroup = findViewById(R.id.packageTypeGroup);
        weightInput = findViewById(R.id.weightInput);
        weightUnitGroup = findViewById(R.id.weightUnitGroup);
        minusButton = findViewById(R.id.minusButton);
        plusButton = findViewById(R.id.plusButton);
        getEstimateButton = findViewById(R.id.getEstimateButton);
        volumetricWeightCheckbox = findViewById(R.id.volumetricWeightCheckbox);
        basicEstimateTitle = findViewById(R.id.basicEstimateTitle);
        originalPrice = findViewById(R.id.originalPrice);
        discountPrice = findViewById(R.id.discountPrice);
        estimatedDeliveryTime = findViewById(R.id.estimatedDeliveryTime);

        // Populate the Spinner with country names and set the placeholder
        List<String> countries = Arrays.asList("Select Country", "India", "USA", "Canada", "Germany", "France");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        // Set placeholder text
        countrySpinner.setSelection(0); // Set the default value to "Select Country"

        // Button Click Listeners
        getEstimateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateShippingEstimate();
            }
        });

        // Increment and Decrement weight input
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseWeight();
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseWeight();
            }
        });

        // Spinner item selection listener
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the country selection
                String selectedCountry = parentView.getItemAtPosition(position).toString();
                if (!selectedCountry.equals("Select Country")) {
                    // Optionally, show a Toast or perform some other action
                    Toast.makeText(ShippingPageActivity.this, "Selected Country: " + selectedCountry, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void increaseWeight() {
        String weightText = weightInput.getText().toString();
        if (!weightText.isEmpty()) {
            double weight = Double.parseDouble(weightText);
            weight += 0.5;  // Increase by 0.5 kg or lb
            weightInput.setText(String.format("%.1f", weight));
        }
    }

    private void decreaseWeight() {
        String weightText = weightInput.getText().toString();
        if (!weightText.isEmpty()) {
            double weight = Double.parseDouble(weightText);
            if (weight > 0.5) {
                weight -= 0.5;  // Decrease by 0.5 kg or lb
                weightInput.setText(String.format("%.1f", weight));
            }
        }
    }

    private void calculateShippingEstimate() {
        // Get selected country
        String selectedCountry = countrySpinner.getSelectedItem().toString();

        // Check if the user has selected a country other than the placeholder
        if ("Select Country".equals(selectedCountry)) {
            Toast.makeText(this, "Please select a country", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get package type (Non-Document, Document, or Temperature Controlled)
        int selectedPackageTypeId = packageTypeGroup.getCheckedRadioButtonId();
        RadioButton selectedPackageType = findViewById(selectedPackageTypeId);
        String packageType = selectedPackageType.getText().toString();

        // Get weight input
        String weightText = weightInput.getText().toString();
        double weight = weightText.isEmpty() ? 0.0 : Double.parseDouble(weightText);

        // Get weight unit (KG or LB)
        int selectedWeightUnitId = weightUnitGroup.getCheckedRadioButtonId();
        RadioButton selectedWeightUnit = findViewById(selectedWeightUnitId);
        String weightUnit = selectedWeightUnit.getText().toString();

        // Check if volumetric weight is selected
        boolean isVolumetricWeight = volumetricWeightCheckbox.isChecked();

        // Check if temperature-controlled option is selected
        boolean isTemperatureControlled = packageType.equals("Temperature Controlled");

        // Calculate shipping cost and estimate
        double shippingCost = calculateCost(weight, selectedCountry, isTemperatureControlled, isVolumetricWeight);

        // Show results
        displayEstimate(shippingCost, packageType);
    }

    private double calculateCost(double weight, String destinationCountry, boolean isTemperatureControlled, boolean isVolumetricWeight) {
        double baseCost = 1000.0;  // Example base cost
        double weightMultiplier = weight * 100;  // Example multiplier for weight
        double temperatureMultiplier = isTemperatureControlled ? 200 : 0;
        double volumetricMultiplier = isVolumetricWeight ? 150 : 0;

        return baseCost + weightMultiplier + temperatureMultiplier + volumetricMultiplier;
    }

    private void displayEstimate(double shippingCost, String packageType) {
        basicEstimateTitle.setText("Shipping Estimate: " + packageType);
        originalPrice.setText("₹" + String.format("%.2f", shippingCost));
        discountPrice.setText("₹" + String.format("%.2f", shippingCost * 0.8)); // Discount applied (20% off)
        estimatedDeliveryTime.setText("Estimated Delivery: 5-7 days");

        Toast.makeText(this, "Shipping cost calculated", Toast.LENGTH_SHORT).show();
    }
}
