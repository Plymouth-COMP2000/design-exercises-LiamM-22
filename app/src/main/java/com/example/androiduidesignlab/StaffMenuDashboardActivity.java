package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StaffMenuDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_menu_dashboard);

        // Back button to staff_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuDashboardActivity.this, StaffDashboardActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener menuCategoryClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuDashboardActivity.this, StaffMenuCategoryActivity.class);
                startActivity(intent);
            }
        };

        findViewById(R.id.btnStarters).setOnClickListener(menuCategoryClickListener);
        findViewById(R.id.btnMains).setOnClickListener(menuCategoryClickListener);
        findViewById(R.id.btnDesserts).setOnClickListener(menuCategoryClickListener);
        findViewById(R.id.btnDrinks).setOnClickListener(menuCategoryClickListener);
        findViewById(R.id.btnOffers).setOnClickListener(menuCategoryClickListener);

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuDashboardActivity.this, StaffReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on this screen
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuDashboardActivity.this, StaffSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}