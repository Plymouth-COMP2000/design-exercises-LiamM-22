package com.example.androiduidesignlab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StaffMenuEditActivity extends AppCompatActivity {

    private EditText etMenuName;
    private EditText etMenuPrice;
    private EditText etMenuImage;
    private EditText etMenuCategory;
    private Button btnSaveChanges;
    private DatabaseHelper db;
    private long menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_menu_edit);

        db = new DatabaseHelper(this);
        etMenuName = findViewById(R.id.etMenuName);
        etMenuPrice = findViewById(R.id.etMenuPrice);
        etMenuImage = findViewById(R.id.etMenuImage);
        etMenuCategory = findViewById(R.id.etMenuCategory);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        menuId = getIntent().getLongExtra("menu_id", -1);
        String menuName = getIntent().getStringExtra("menu_name");
        double menuPrice = getIntent().getDoubleExtra("menu_price", 0.0);
        String menuImage = getIntent().getStringExtra("menu_image");
        String menuCategory = getIntent().getStringExtra("menu_category");

        if (menuId != -1) {
            etMenuName.setText(menuName);
            etMenuPrice.setText(String.valueOf(menuPrice));
            etMenuImage.setText(menuImage);
            etMenuCategory.setText(menuCategory);
        } else {
            etMenuCategory.setText(menuCategory);
        }

        btnSaveChanges.setOnClickListener(v -> {
            String updatedName = etMenuName.getText().toString();
            String priceString = etMenuPrice.getText().toString();
            String updatedImage = etMenuImage.getText().toString();
            String updatedCategory = etMenuCategory.getText().toString();
            double updatedPrice = 0.0;

            if (updatedName.isEmpty()) {
                Toast.makeText(this, "Please enter a menu item name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!priceString.isEmpty()) {
                try {
                    updatedPrice = Double.parseDouble(priceString);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (menuId != -1) {
                db.updateMenuItem(new Menu(menuId, updatedName, updatedPrice, updatedImage, updatedCategory));
            } else {
                db.addMenuItem(new Menu(updatedName, updatedPrice, updatedImage, updatedCategory));
            }
            finish();
        });
    }
}
