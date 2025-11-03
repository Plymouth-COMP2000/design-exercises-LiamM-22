package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerReservationMakingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_making);

        // Back button to customer_reservation_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener availableTimesClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerReservationFinalisationActivity.class);
                startActivity(intent);
            }
        };

        findViewById(R.id.availableTimes1).setOnClickListener(availableTimesClickListener);
        findViewById(R.id.availableTimes2).setOnClickListener(availableTimesClickListener);

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationMakingActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}