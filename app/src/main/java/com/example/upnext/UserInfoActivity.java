package com.example.upnext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class UserInfoActivity extends AppCompatActivity {

    private TextView displayUsername, displayEmail;
    private MaterialButton btnEditInfo, btnSignOut, btnAboutDeveloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        // for connect UI Elements
        displayUsername = findViewById(R.id.displayUsername);
        displayEmail = findViewById(R.id.displayEmail);
        btnEditInfo = findViewById(R.id.btnEditInfo);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnAboutDeveloper = findViewById(R.id.btnAboutDeveloper);

        // when click the Edit Info button
        btnEditInfo.setOnClickListener(v -> showEditDialog());

        // when click the Sign-out button
        btnSignOut.setOnClickListener(v -> showSignOutDialog());

        // when click the About Developer button
        btnAboutDeveloper.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, DevInfoActivity.class);
            startActivity(intent);
        });
    }

    // Edit Dialog Box: Update data and Cancel data
    private void showEditDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_info, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        EditText editUsername = view.findViewById(R.id.editUsername);
        Button btnOk = view.findViewById(R.id.btnDialogOk);
        Button btnCancel = view.findViewById(R.id.btnDialogCancel);

        // Providing the current name on the screen to the dialog
        String currentName = displayUsername.getText().toString().replace("Username : ", "");
        editUsername.setText(currentName);

        // when click OK button, the data will be updated and the dialog box will close.
        btnOk.setOnClickListener(v -> {
            String newName = editUsername.getText().toString().trim();
            if (!newName.isEmpty()) {
                displayUsername.setText("Username : " + newName);
                Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                editUsername.setError("Username cannot be empty");
            }
        });

        //When Cancel is pressed, nothing happens and only the Dialog closes
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    //Sign-out Dialog Box: go to the Splash Screen and  Cancel
    private void showSignOutDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sign_out, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button btnOk = view.findViewById(R.id.btnSignOutOk);
        Button btnCancel = view.findViewById(R.id.btnSignOutCancel);

        // when click OK button, then go to the Splash Screen and close all the other screens.
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(UserInfoActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishAffinity(); // Removes all old activities
        });

        // When the Cancel button is pressed, nothing happens and the user remains on the User Info Screen
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}