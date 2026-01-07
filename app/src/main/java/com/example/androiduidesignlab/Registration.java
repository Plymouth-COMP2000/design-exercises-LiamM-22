package com.example.androiduidesignlab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    private EditText etUsername, etFirstName, etLastName, etEmail, etContact, etPassword, etConfirmPassword;
    private Button btnCreateAccount;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        apiService = ApiService.getInstance(this);

        // Initialize views
        etUsername = findViewById(R.id.etUsernameInput);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContactNumber);
        etPassword = findViewById(R.id.etPasswordInput);
        etConfirmPassword = findViewById(R.id.etConfirmPasswordInput);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Back buttons
        findViewById(R.id.ivBackArrow).setOnClickListener(v -> finish());
        findViewById(R.id.tvBackText).setOnClickListener(v -> finish());

        // Create account button listener
        btnCreateAccount.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {
        String username = etUsername.getText().toString().trim();
        String firstname = etFirstName.getText().toString().trim();
        String lastname = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String usertype = "student"; // Hardcoded to student

        // Validation
        if (username.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.createUser(username, password, firstname, lastname, email, contact, usertype, new ApiService.ApiResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Toast.makeText(Registration.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to login screen
            }

            @Override
            public void onError(String message) {
                Toast.makeText(Registration.this, "Registration failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
