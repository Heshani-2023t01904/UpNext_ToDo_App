package com.example.upnext;

import android.content.Intent; // Imported Intent class
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText usernameInput, passwordInput, confirmPasswordInput, emailInput;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        // Initialize UI components by their IDs
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        emailInput = findViewById(R.id.emailInput);
        signupButton = findViewById(R.id.signupButton);

        // Set up click listener for the Sign Up button
        signupButton.setOnClickListener(v -> {
            String user = usernameInput.getText().toString().trim();
            String pass = passwordInput.getText().toString().trim();
            String confirmPass = confirmPasswordInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();

            // Check if all fields are filled
            if (user.isEmpty() || pass.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            // Validate if passwords match
            else if (!pass.equals(confirmPass)) {
                confirmPasswordInput.setError("Passwords do not match!");
            }
            // Validate password strength based on the pattern
            else if (!isValidPassword(pass)) {
                passwordInput.setError("Must have 8+ chars, 1 Uppercase, 1 Number, 1 Symbol.");
            }
            else {
                // Save user credentials locally using SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("saved_username", user);
                editor.putString("saved_password", pass);
                editor.apply();

                // Inform the user that the account is created
                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();

                // Navigate directly to the To-Do Dashboard
                Intent intent = new Intent(SignUpActivity.this, ToDoActivity.class);
                startActivity(intent);

                // Close SignUpActivity so the user cannot go back to it
                finish();
            }
        });
    }


    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(passwordPattern);
    }
}