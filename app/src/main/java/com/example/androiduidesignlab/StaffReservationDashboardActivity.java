package com.example.androiduidesignlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StaffReservationDashboardActivity extends AppCompatActivity implements ReservationsAdapter.OnReservationListener {

    private static final int NOTIFICATION_PERMISSION_CODE = 101;
    private RecyclerView rvReservations;
    private ReservationsAdapter reservationsAdapter;
    private List<Reservation> reservationList;
    private DatabaseHelper db;
    private NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reservation_dashboard);

        db = new DatabaseHelper(this);
        notificationService = new NotificationService(this);

        // Request notification permission if not already granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
        }

        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffDashboardActivity.class);
            startActivity(intent);
        });

        rvReservations = findViewById(R.id.rvReservations);
        rvReservations.setLayoutManager(new LinearLayoutManager(this));

        reservationList = db.getAllReservations();
        reservationsAdapter = new ReservationsAdapter(reservationList, this);
        rvReservations.setAdapter(reservationsAdapter);

        FloatingActionButton fabAddReservation = findViewById(R.id.fabAddReservation);
        fabAddReservation.setOnClickListener(view -> {
            db.addReservation(new Reservation("New reservation"));
            refreshReservationList();
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(v -> {});

        findViewById(R.id.staffBtnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffMenuDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffSettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshReservationList();
    }

    private void refreshReservationList() {
        reservationList.clear();
        reservationList.addAll(db.getAllReservations());
        reservationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(long id, String details) {
        Intent intent = new Intent(this, StaffReservationEditActivity.class);
        intent.putExtra("reservation_id", id);
        intent.putExtra("reservation_details", details);
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(long id) {
        db.deleteReservation(id);
        
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean("notifications_enabled_staff", true);

        if (notificationsEnabled) {
            notificationService.showNotification("Reservation Cancelled", "A reservation has been cancelled.");
        }

        refreshReservationList();
    }
}
