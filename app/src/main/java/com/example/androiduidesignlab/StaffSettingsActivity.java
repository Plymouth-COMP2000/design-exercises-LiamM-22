package com.example.androiduidesignlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class StaffSettingsActivity extends AppCompatActivity {

    private Switch switchNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_settings);

        switchNotifications = findViewById(R.id.switchNotifications);

        // Load preference
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean("notifications_enabled_staff", true);
        switchNotifications.setChecked(notificationsEnabled);

        // Save preference on change
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("notifications_enabled_staff", isChecked);
            editor.apply();
        });

        // Back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            Intent intent = new Intent(StaffSettingsActivity.this, StaffReservationDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(StaffSettingsActivity.this, StaffMenuDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            // Already on this screen
        });
    }
}
