package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText signupName, signupEmail, signupPassword, signupConfirmPassword;
    private Button signupButton;
    private TextView footerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);  // Make sure to use the correct layout name

        // Initialize Views
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirm_password);
        signupButton = findViewById(R.id.signup_button);
        footerText = findViewById(R.id.footer_text);



        // Sign Up Button OnClickListener
        signupButton.setOnClickListener(v -> {
            // Get user inputs
            String name = signupName.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String confirmPassword = signupConfirmPassword.getText().toString().trim();

            // Validate inputs
            if (TextUtils.isEmpty(name)) {
                signupName.setError("Name is required");
            } else if (TextUtils.isEmpty(email)) {
                signupEmail.setError("Email is required");
            } else if (TextUtils.isEmpty(password)) {
                signupPassword.setError("Password is required");
            } else if (!password.equals(confirmPassword)) {
                signupConfirmPassword.setError("Passwords do not match");
            } else {
                // Simulate successful sign-up
                Toast.makeText(SignupActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();

                // Redirect to Login Activity or Home Activity
                Intent intent = new Intent(SignupActivity.this, HomePage.class);
                startActivity(intent);
                finish();  // Close the sign-up activity
            }
        });
    }

    // Go to Login Activity (called by the footer text click)
    public void goToLogin(View view) {
        Intent intent = new Intent(SignupActivity.this, SplashActivity.class);
        startActivity(intent);
    }
}
