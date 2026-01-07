package com.example.androiduidesignlab;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class CustomerReservationFinalisationActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 102;
    private DatabaseHelper db;
    private NotificationService notificationService;
    private long reservationId = -1;
    private String reservationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_finalisation);

        db = new DatabaseHelper(this);
        notificationService = new NotificationService(this);

        // Request notification permission if not already granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
        }

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        reservationDetails = getIntent().getStringExtra("reservation_details");

        TextView tvReservationDetails = findViewById(R.id.tvReservationDetails);
        if (reservationDetails != null) {
            tvReservationDetails.setText(reservationDetails);
        }

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        Button btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnConfirmBooking.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            boolean notificationsEnabled = prefs.getBoolean("notifications_enabled_customer", true);

            if (reservationId != -1) {
                db.updateReservation(new Reservation(reservationId, reservationDetails));
                if (notificationsEnabled) {
                    notificationService.showNotification("Reservation Updated", "Your reservation has been successfully updated.");
                }
            } else {
                db.addReservation(new Reservation(reservationDetails));
                if (notificationsEnabled) {
                    notificationService.showNotification("New Reservation!", "A new reservation has been made.");
                }
            }
            Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerReservationDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerReservationDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationFinalisationActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationFinalisationActivity.this, CustomerSettingsActivity.class);
            startActivity(intent);
        });
    }
}
