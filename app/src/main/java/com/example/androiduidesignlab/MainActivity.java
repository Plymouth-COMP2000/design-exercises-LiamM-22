package com.example.androiduidesignlab;

import android.content.Intent;
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
}
