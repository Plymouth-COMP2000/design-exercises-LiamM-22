package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StaffReservationEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reservation_edit);

        // Back button to staff_reservation_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationEditActivity.this, StaffReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        // Confirm Booking button
        findViewById(R.id.btnConfirmBooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationEditActivity.this, StaffReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationEditActivity.this, StaffMenuDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationEditActivity.this, StaffSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}