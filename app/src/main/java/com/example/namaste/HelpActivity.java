package com.example.namaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView helpTitle, faqTitle, contactUsTitle, feedbackTitle, footerText;
    private EditText feedbackInput;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help); // Use your layout file name here

        // Initialize Views
        backButton = findViewById(R.id.back_button);
        helpTitle = findViewById(R.id.help_title);
        faqTitle = findViewById(R.id.faq_title);
        contactUsTitle = findViewById(R.id.contact_us_title);
        feedbackTitle = findViewById(R.id.feedback_title);
        footerText = findViewById(R.id.footer_text);
        feedbackInput = findViewById(R.id.feedback_input);
        nextButton = findViewById(R.id.next_button);

        // Back Button Action
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click, go back to previous screen
                onBackPressed();
            }
        });

        // Next Button Action
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the next button click, for example, submitting the feedback
                String feedback = feedbackInput.getText().toString();
                if (!feedback.isEmpty()) {
                    // Logic to submit feedback (e.g., send to a server, save locally, etc.)
                    // For now, just display the feedback in a toast message (you can replace it with your own functionality)
                    Intent intent = new Intent(HelpActivity.this, HomePage.class);
                    intent.putExtra("feedback", feedback);
                    startActivity(intent);
                } else {
                    // If no feedback, show a message
                    feedbackInput.setError("Please provide your feedback");
                }
            }
        });
    }
}
