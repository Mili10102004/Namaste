package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignupActivity extends AppCompatActivity {

    private EditText signupName, signupEmail, signupPassword, signupConfirmPassword;
    private Button signupButton;
    private TextView footerText;

    // Firebase Realtime Database reference
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

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

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");  // Reference to 'users' node

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
                // Hash the password
                String hashedPassword = hashPassword(password);

                // Create a User object
                User newUser = new User(name, email, hashedPassword);

                // Store the user data in Firebase Realtime Database under the 'users' node
                String userId = usersRef.push().getKey();  // Generate unique ID for the user
                if (userId != null) {
                    usersRef.child(userId).setValue(newUser)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Simulate successful sign-up
                                    Toast.makeText(SignupActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();

                                    // Redirect to Home Page
                                    Intent intent = new Intent(SignupActivity.this, HomePage.class);
                                    startActivity(intent);
                                    finish();  // Close the sign-up activity
                                } else {
                                    Toast.makeText(SignupActivity.this, "Failed to sign up. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Footer text click listener (for login page navigation)
        footerText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, SplashActivity.class);
            startActivity(intent);
        });
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.encodeToString(hashBytes, Base64.DEFAULT);  // Encode to Base64 to store it as a string
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to navigate directly to Home Page (without filling out the form)
    public void skipToHome(View view) {
        Intent intent = new Intent(SignupActivity.this, HomePage.class);
        startActivity(intent);
        finish();  // Close the sign-up activity
    }    
}
