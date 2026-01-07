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

        View.OnClickListener menuCategoryClickListener = v -> {
            Intent intent = new Intent(StaffMenuDashboardActivity.this, StaffMenuCategoryActivity.class);
            String category = "";
            int id = v.getId();
            if (id == R.id.btnStarters) {
                category = "Starters";
            } else if (id == R.id.btnMains) {
                category = "Mains";
            } else if (id == R.id.btnDesserts) {
                category = "Desserts";
            } else if (id == R.id.btnDrinks) {
                category = "Drinks";
            } else if (id == R.id.btnOffers) {
                category = "Offers";
            }
            intent.putExtra("category", category);
            startActivity(intent);
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
