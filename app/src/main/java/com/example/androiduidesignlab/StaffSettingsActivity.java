package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StaffSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_settings);

        // Back button to staff_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffSettingsActivity.this, StaffDashboardActivity.class);
                startActivity(intent);
            }
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffSettingsActivity.this, StaffReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffSettingsActivity.this, StaffMenuDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on this screen
            }
        });
    }
}