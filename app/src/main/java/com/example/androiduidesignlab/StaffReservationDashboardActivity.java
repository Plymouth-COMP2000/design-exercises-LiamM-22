package com.example.androiduidesignlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StaffReservationDashboardActivity extends AppCompatActivity implements ReservationsAdapter.OnReservationListener {

    private static final int NOTIFICATION_PERMISSION_CODE = 101;
    private RecyclerView rvReservations;
    private ReservationsAdapter reservationsAdapter;
    private List<Reservation> reservationList;
    private DatabaseHelper db;
    private NotificationService notificationService;
    private CalendarView calendarView;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reservation_dashboard);

        db = new DatabaseHelper(this);
        notificationService = new NotificationService(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
        }

        rvReservations = findViewById(R.id.rvReservations);
        rvReservations.setLayoutManager(new LinearLayoutManager(this));

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
            refreshReservationList();
        });

        reservationList = new java.util.ArrayList<>();
        reservationsAdapter = new ReservationsAdapter(reservationList, this);
        rvReservations.setAdapter(reservationsAdapter);

        FloatingActionButton fabAddReservation = findViewById(R.id.fabAddReservation);
        fabAddReservation.setOnClickListener(view -> {
            db.addReservation(new Reservation("staff", selectedDate, "12:00", 2, "New Walk-in"));
            refreshReservationList();
        });

        findViewById(R.id.btnHome).setOnClickListener(v -> startActivity(new Intent(this, StaffDashboardActivity.class)));
        findViewById(R.id.btnReservations).setOnClickListener(v -> {});
        findViewById(R.id.staffBtnMenu).setOnClickListener(v -> startActivity(new Intent(this, StaffMenuDashboardActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v -> startActivity(new Intent(this, StaffSettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = sdf.format(Calendar.getInstance().getTime());
        calendarView.setDate(System.currentTimeMillis(), false, true);
        refreshReservationList();
    }

    private void refreshReservationList() {
        reservationList.clear();
        reservationList.addAll(db.getReservationsByDate(selectedDate));
        reservationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(long id, String username, String date, String time, int guests, String details) {
        Intent intent = new Intent(this, StaffReservationEditActivity.class);
        intent.putExtra("reservation_id", id);
        intent.putExtra("username", username);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("guests", guests);
        intent.putExtra("details", details);
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(long id) {
        db.deleteReservation(id);
        
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        if (prefs.getBoolean("notifications_enabled_staff", true)) {
            notificationService.showNotification("Reservation Cancelled", "A reservation has been cancelled.");
        }

        refreshReservationList();
    }
}
