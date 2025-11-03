package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StaffReservationDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reservation_dashboard);

        // Back button to staff_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffDashboardActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener editReservationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffReservationEditActivity.class);
                startActivity(intent);
            }
        };

        findViewById(R.id.btnEditReservation1).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation2).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation3).setOnClickListener(editReservationClickListener);

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on this screen
            }
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffMenuDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}