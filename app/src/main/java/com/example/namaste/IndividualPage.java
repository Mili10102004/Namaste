package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IndividualPage extends AppCompatActivity {

    private CheckBox checkBooks, checkClothing, checkElectronics, checkJewellery;
    private CheckBox checkFood, checkMedicine;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_page);

        // Initialize Firebase Database with custom URL
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://namaste-e16fa-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("individual_shipping");

        // Initialize Item Category CheckBoxes
        checkBooks = findViewById(R.id.checkBooks);
        checkClothing = findViewById(R.id.checkClothing);
        checkElectronics = findViewById(R.id.checkElectronics);
        checkJewellery = findViewById(R.id.checkJewellery);

        // Initialize Temperature Control Item CheckBoxes
        checkFood = findViewById(R.id.checkFood);
        checkMedicine = findViewById(R.id.checkMedicine);

        // CheckBox logic
        CheckBox[] itemCategoryBoxes = {checkBooks, checkClothing, checkElectronics, checkJewellery};
        CheckBox[] tempControlBoxes = {checkFood, checkMedicine};

        setupCheckBoxListeners(itemCategoryBoxes, tempControlBoxes);

        // Initialize Next button
        findViewById(R.id.btnNext).setOnClickListener(v -> proceedToNextPage());
    }

    private void setupCheckBoxListeners(CheckBox[] itemCategoryBoxes, CheckBox[] tempControlBoxes) {
        for (CheckBox box : itemCategoryBoxes) {
            box.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) setCheckBoxesEnabled(tempControlBoxes, false);
                else if (!isAnyCheckBoxChecked(itemCategoryBoxes)) setCheckBoxesEnabled(tempControlBoxes, true);
            });
        }

        for (CheckBox box : tempControlBoxes) {
            box.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) setCheckBoxesEnabled(itemCategoryBoxes, false);
                else if (!isAnyCheckBoxChecked(tempControlBoxes)) setCheckBoxesEnabled(itemCategoryBoxes, true);
            });
        }
    }

    private void setCheckBoxesEnabled(CheckBox[] checkBoxes, boolean enabled) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setEnabled(enabled);
        }
    }

    private boolean isAnyCheckBoxChecked(CheckBox[] checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) return true;
        }
        return false;
    }

    private void proceedToNextPage() {
        // Collect selected categories
        StringBuilder selectedItems = new StringBuilder();
        if (checkBooks.isChecked()) selectedItems.append("Books, ");
        if (checkClothing.isChecked()) selectedItems.append("Clothing, ");
        if (checkElectronics.isChecked()) selectedItems.append("Electronics, ");
        if (checkJewellery.isChecked()) selectedItems.append("Jewellery, ");
        if (checkFood.isChecked()) selectedItems.append("Food, ");
        if (checkMedicine.isChecked()) selectedItems.append("Medicine, ");

        if (selectedItems.length() == 0) {
            Toast.makeText(this, "Please select at least one item.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Remove trailing comma
        String selectedItemsString = selectedItems.substring(0, selectedItems.length() - 2);

        // Save data and navigate
        Intent intent = new Intent(IndividualPage.this, SendPackageActivity.class);
        intent.putExtra("selectedItems", selectedItemsString); // Pass selected items
        startActivity(intent);
    }
}
