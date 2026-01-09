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

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean("notifications_enabled_staff", true);
        switchNotifications.setChecked(notificationsEnabled);

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("notifications_enabled_staff", isChecked);
            editor.apply();
        });

        findViewById(R.id.btnHome).setOnClickListener(v -> startActivity(new Intent(this, StaffDashboardActivity.class)));
        findViewById(R.id.btnReservations).setOnClickListener(v -> startActivity(new Intent(this, StaffReservationDashboardActivity.class)));
        findViewById(R.id.staffBtnMenu).setOnClickListener(v -> startActivity(new Intent(this, StaffMenuDashboardActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v -> {});
    }
}
