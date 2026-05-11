package com.example.upnext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText loginUsername, loginPassword;
    private Button btnLogin;
    private TextView txtSignUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_screen);

        // Initialize UI components
        loginUsername = findViewById(R.id.usernameInput);
        loginPassword = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.loginButton);
        txtSignUpLink = findViewById(R.id.txtSignUp);

        // Safety Check: If IDs are wrong, show a toast instead of crashing
        if (btnLogin == null || loginUsername == null || loginPassword == null) {
            Toast.makeText(this, "UI ID Mismatch! Please check XML IDs.", Toast.LENGTH_LONG).show();
            return;
        }

        btnLogin.setOnClickListener(v -> {
            String enteredUser = loginUsername.getText().toString().trim();
            String enteredPass = loginPassword.getText().toString().trim();

            if (enteredUser.isEmpty() || enteredPass.isEmpty()) {
                Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String savedUser = sharedPref.getString("saved_username", "");
            String savedPass = sharedPref.getString("saved_password", "");

            // Logic: Check if credentials match
            if (!savedUser.isEmpty() && enteredUser.equals(savedUser) && enteredPass.equals(savedPass)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this, ToDoActivity.class);
                startActivity(intent);
                finish();
            } else {
                showNoAccountDialog();
            }
        });

        if (txtSignUpLink != null) {
            txtSignUpLink.setOnClickListener(v -> showNoAccountDialog());
        }
    }

    private void showNoAccountDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialogbox_signup, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button btnGoToSignup = dialogView.findViewById(R.id.btnGoToSignup);
        Button btnNotNow = dialogView.findViewById(R.id.btnCancelNoAccount);

        if (btnGoToSignup != null) {
            btnGoToSignup.setOnClickListener(v -> {
                dialog.dismiss();
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            });
        }

        if (btnNotNow != null) {
            btnNotNow.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }
}