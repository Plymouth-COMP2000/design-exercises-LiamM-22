package com.example.androiduidesignlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiService.getInstance(this);

        // Temporary user creation
        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        if (!prefs.getBoolean("db_and_users_created", false)) {
            createDatabaseAndInitialUsers();
            prefs.edit().putBoolean("db_and_users_created", true).apply();
        }

        Button loginButton = findViewById(R.id.LoginButton);
        final EditText usernameInput = findViewById(R.id.UsernameInput);
        final EditText passwordInput = findViewById(R.id.PasswordInput);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            apiService.login(username, password, new ApiService.ApiResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        String userType = response.getString("usertype");
                        if ("staff".equalsIgnoreCase(userType)) {
                            Intent intent = new Intent(MainActivity.this, StaffDashboardActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error parsing user data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button createAccountButton = findViewById(R.id.CreateAccountButton);
        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        });
    }

    private void createDatabaseAndInitialUsers() {
        apiService.createStudentDatabase(new ApiService.ApiStringListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, "Student database created/verified", Toast.LENGTH_SHORT).show();
                createInitialUsers();
            }

            @Override
            public void onError(String message) {
                // This can happen if the db already exists, which is fine.
                // We can proceed to create the users anyway.
                Toast.makeText(MainActivity.this, "Database verification failed, proceeding...", Toast.LENGTH_SHORT).show();
                createInitialUsers();
            }
        });
    }

    private void createInitialUsers() {
        // Create staff user
        apiService.createUser("staff", "password123", "", "", "", "", "staff", new ApiService.ApiResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Toast.makeText(MainActivity.this, "Staff user created successfully", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Staff user creation failed: " + message, Toast.LENGTH_LONG).show();
            }
        });

        // Create customer user
        apiService.createUser("customer", "customer123", "", "", "", "", "student", new ApiService.ApiResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Toast.makeText(MainActivity.this, "Customer user created successfully", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Customer user creation failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
