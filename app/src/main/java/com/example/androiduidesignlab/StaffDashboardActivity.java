package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StaffDashboardActivity extends AppCompatActivity implements ReservationsAdapter.OnReservationListener {

    private RecyclerView rvTodaysReservations;
    private ReservationsAdapter reservationsAdapter;
    private List<Reservation> todaysReservationList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_dashboard);

        db = new DatabaseHelper(this);

        rvTodaysReservations = findViewById(R.id.rvTodaysReservations);
        rvTodaysReservations.setLayoutManager(new LinearLayoutManager(this));

        todaysReservationList = new java.util.ArrayList<>();
        reservationsAdapter = new ReservationsAdapter(todaysReservationList, this);
        rvTodaysReservations.setAdapter(reservationsAdapter);

        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            startActivity(new Intent(StaffDashboardActivity.this, StaffReservationDashboardActivity.class));
        });
        findViewById(R.id.staffBtnMenu).setOnClickListener(v -> {
            startActivity(new Intent(StaffDashboardActivity.this, StaffMenuDashboardActivity.class));
        });
        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            startActivity(new Intent(StaffDashboardActivity.this, StaffSettingsActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTodaysReservations();
    }

    private void refreshTodaysReservations() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(Calendar.getInstance().getTime());
        
        todaysReservationList.clear();
        todaysReservationList.addAll(db.getReservationsByDate(today));
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
        refreshTodaysReservations();
    }
}
