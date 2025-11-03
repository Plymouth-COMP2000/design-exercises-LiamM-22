package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dashboard);

        View.OnClickListener menuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MenuCategoryActivity.class);
                startActivity(intent);
            }
        };

        findViewById(R.id.btnStarters).setOnClickListener(menuClickListener);
        findViewById(R.id.btnMains).setOnClickListener(menuClickListener);
        findViewById(R.id.btnDesserts).setOnClickListener(menuClickListener);
        findViewById(R.id.btnDrinks).setOnClickListener(menuClickListener);
        findViewById(R.id.btnOffers).setOnClickListener(menuClickListener);

        findViewById(R.id.btnReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomerReservationDashboardActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CustomerSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}