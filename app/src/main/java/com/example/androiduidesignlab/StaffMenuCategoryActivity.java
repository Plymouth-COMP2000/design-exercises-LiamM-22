package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StaffMenuCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_menu_category);

        // Back button to staff_menu_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffMenuDashboardActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener editItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuCategoryActivity.this, AddEditMenuItemActivity.class);
                startActivity(intent);
            }
        };

        findViewById(R.id.btnEditItem1).setOnClickListener(editItemClickListener);
        findViewById(R.id.btnEditItem2).setOnClickListener(editItemClickListener);
        findViewById(R.id.btnEditItem3).setOnClickListener(editItemClickListener);
        findViewById(R.id.btnEditItem4).setOnClickListener(editItemClickListener);
        findViewById(R.id.btnAddNewItem).setOnClickListener(editItemClickListener);

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffMenuDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}