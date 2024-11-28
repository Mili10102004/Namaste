package com.example.namaste;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PackageDetailsActivity extends AppCompatActivity {

    private RadioGroup radioGroupSize;
    private CheckBox checkBooks, checkClothing, checkFood, checkElectronics, checkMedicine, checkJewellery;
    private Button btnAddAnotherPackage, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        // Initialize views
        radioGroupSize = findViewById(R.id.radioGroupSize);
        checkBooks = findViewById(R.id.checkBooks);
        checkClothing = findViewById(R.id.checkClothing);
        checkFood = findViewById(R.id.checkFood);
        checkElectronics = findViewById(R.id.checkElectronics);
        checkMedicine = findViewById(R.id.checkMedicine);
        checkJewellery = findViewById(R.id.checkJewellery);
        btnAddAnotherPackage = findViewById(R.id.btnAddAnotherPackage);
        btnNext = findViewById(R.id.btnNext);

        // Handle button clicks
        btnAddAnotherPackage.setOnClickListener(v ->
                Toast.makeText(this, "Add another package clicked", Toast.LENGTH_SHORT).show());

        btnNext.setOnClickListener(v ->
                Toast.makeText(this, "Proceeding to Next", Toast.LENGTH_SHORT).show());
    }
}
