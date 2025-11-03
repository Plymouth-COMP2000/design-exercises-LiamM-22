package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerReservationFinalisationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_finalisation);

        // Back button to customer_reservation_making
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerReservationMakingActivity.class);
                startActivity(intent);
            }
        });

        // Confirm Booking Button
        findViewById(R.id.btnConfirmBooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationFinalisationActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}