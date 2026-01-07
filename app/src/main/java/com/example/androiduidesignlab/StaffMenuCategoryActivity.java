package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StaffMenuCategoryActivity extends AppCompatActivity implements MenuAdapter.OnMenuListener {

    private RecyclerView rvMenuItems;
    private MenuAdapter menuAdapter;
    private List<Menu> menuList;
    private DatabaseHelper db;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_menu_category);

        db = new DatabaseHelper(this);
        category = getIntent().getStringExtra("category");

        TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvCategoryTitle.setText(category);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        rvMenuItems = findViewById(R.id.rvMenuItems);
        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));

        menuList = db.getMenuItemsByCategory(category);
        menuAdapter = new MenuAdapter(menuList, this);
        rvMenuItems.setAdapter(menuAdapter);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffMenuEditActivity.class);
            intent.putExtra("menu_category", category);
            startActivity(intent);
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffReservationDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.staffBtnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffMenuDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffSettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMenuList();
    }

    private void refreshMenuList() {
        menuList.clear();
        menuList.addAll(db.getMenuItemsByCategory(category));
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(long id, String name, double price, String image, String category) {
        Intent intent = new Intent(StaffMenuCategoryActivity.this, StaffMenuEditActivity.class);
        intent.putExtra("menu_id", id);
        intent.putExtra("menu_name", name);
        intent.putExtra("menu_price", price);
        intent.putExtra("menu_image", image);
        intent.putExtra("menu_category", category);
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(long id) {
        db.deleteMenuItem(id);
        refreshMenuList();
    }
}
