package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerReservationFinalisationActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private long reservationId = -1;
    private String reservationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_finalisation);

        db = new DatabaseHelper(this);

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        reservationDetails = getIntent().getStringExtra("reservation_details");

        TextView tvReservationDetails = findViewById(R.id.tvReservationDetails);
        if (reservationDetails != null) {
            tvReservationDetails.setText(reservationDetails);
        }

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        Button btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnConfirmBooking.setOnClickListener(v -> {
            if (reservationId != -1) {
                db.updateReservation(new Reservation(reservationId, reservationDetails));
            } else {
                db.addReservation(new Reservation(reservationDetails));
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
