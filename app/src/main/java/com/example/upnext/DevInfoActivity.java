package com.example.upnext;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DevInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info);

        // to identify the Exit button
        MaterialButton btnExitDev = findViewById(R.id.btnExitDev);

        // When the Exit button is pressed, this screen closes and returns to the previous screen
        btnExitDev.setOnClickListener(v -> {
            finish(); // It closes the currently existing Activity
        });
    }
}