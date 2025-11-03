package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StaffDashboardActivity extends AppCompatActivity {

    private static final String TAG = "StaffDashboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_dashboard);

        // --- Edit Reservation Buttons --- //
        View.OnClickListener editReservationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffDashboardActivity.this, StaffReservationEditActivity.class);
                startActivity(intent);
            }
        };
        findViewById(R.id.btnEditReservation1).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation2).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation3).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation4).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation5).setOnClickListener(editReservationClickListener);
        findViewById(R.id.btnEditReservation6).setOnClickListener(editReservationClickListener);



        // Bottom navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Reservations button clicked");
                Intent intent = new Intent(StaffDashboardActivity.this, StaffReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ADDED LOGGING
                Log.d(TAG, "staffBtnMenu CLICKED! Attempting to start StaffMenuDashboardActivity.");
                Intent intent = new Intent(StaffDashboardActivity.this, StaffMenuDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Profile button clicked");
                Intent intent = new Intent(StaffDashboardActivity.this, StaffSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}