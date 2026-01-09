package com.example.androiduidesignlab;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class CustomerReservationFinalisationActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 102;
    private DatabaseHelper db;
    private NotificationService notificationService;
    private long reservationId = -1;
    private String date;
    private String time;
    private int guests;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_finalisation);

        db = new DatabaseHelper(this);
        notificationService = new NotificationService(this);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        currentUsername = prefs.getString("username", null);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
        }

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        guests = getIntent().getIntExtra("guests", 1);

        TextView tvReservationDetails = findViewById(R.id.tvReservationDetails);
        tvReservationDetails.setText("Confirm reservation for " + guests + " on " + date + " at " + time);
        
        EditText etDetails = findViewById(R.id.etReservationDetails);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        Button btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnConfirmBooking.setOnClickListener(v -> {
            boolean notificationsEnabled = prefs.getBoolean("notifications_enabled_customer", true);
            String details = etDetails.getText().toString();

            if (currentUsername == null) {
                return;
            }

            if (reservationId != -1) {
                db.updateReservation(new Reservation(reservationId, currentUsername, date, time, guests, details));
                if (notificationsEnabled) {
                    notificationService.showNotification("Reservation Updated", "Your reservation has been successfully updated.");
                }
            } else {
                db.addReservation(new Reservation(currentUsername, date, time, guests, details));
                if (notificationsEnabled) {
                    notificationService.showNotification("New Reservation!", "A new reservation has been made.");
                }
            }
            Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerReservationDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnReservations).setOnClickListener(v -> startActivity(new Intent(this, CustomerReservationDashboardActivity.class)));
        findViewById(R.id.btnMenu).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v -> startActivity(new Intent(this, CustomerSettingsActivity.class)));
    }
}
