package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuCategoryActivity extends AppCompatActivity {

    private RecyclerView rvMenuItems;
    private GuestMenuAdapter menuAdapter;
    private List<Menu> menuList;
    private DatabaseHelper db;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_category);

        db = new DatabaseHelper(this);
        category = getIntent().getStringExtra("category");

        TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvCategoryTitle.setText(category);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        rvMenuItems = findViewById(R.id.rvMenuItems);
        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));

        menuList = db.getMenuItemsByCategory(category);
        menuAdapter = new GuestMenuAdapter(menuList);
        rvMenuItems.setAdapter(menuAdapter);

        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            Intent intent = new Intent(MenuCategoryActivity.this, CustomerReservationDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(MenuCategoryActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(MenuCategoryActivity.this, CustomerSettingsActivity.class);
            startActivity(intent);
        });
    }
}
