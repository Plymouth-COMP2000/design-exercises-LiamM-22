package com.example.androiduidesignlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private ProgressBar progressBar;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiService.getInstance(this);
        progressBar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.LoginButton);

        final EditText usernameInput = findViewById(R.id.UsernameInput);
        final EditText passwordInput = findViewById(R.id.PasswordInput);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            showLoading(true);

            apiService.login(username, password, new ApiService.ApiResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    showLoading(false);
                    try {
                        String userType = response.getString("usertype");
                        String loggedInUsername = response.getString("username");
                        
                        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                        prefs.edit().putString("username", loggedInUsername).apply();

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
                    showLoading(false);
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

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            loginButton.setEnabled(true);
        }
    }
}
