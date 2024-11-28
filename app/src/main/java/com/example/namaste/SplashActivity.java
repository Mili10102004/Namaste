package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout screen1, screen2;  // Using LinearLayout to match the layout's root view type
    private Button btnNext1, btnNext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  // Ensure the layout is correctly referenced

        // Initialize views
        screen1 = findViewById(R.id.screen1);
        screen2 = findViewById(R.id.screen2);
        btnNext1 = findViewById(R.id.btnNext1);
        btnNext2 = findViewById(R.id.btnNext2);

        // Button click to switch to Screen 2
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide Screen 1 and show Screen 2
                screen1.setVisibility(View.GONE);
                screen2.setVisibility(View.VISIBLE);
            }
        });

        // Button click to navigate to the next activity
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity (e.g., SignupActivity)
                Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
