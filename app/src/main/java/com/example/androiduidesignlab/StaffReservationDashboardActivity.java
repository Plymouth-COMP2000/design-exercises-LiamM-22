package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StaffReservationDashboardActivity extends AppCompatActivity implements ReservationsAdapter.OnReservationListener {

    private RecyclerView rvReservations;
    private ReservationsAdapter reservationsAdapter;
    private List<Reservation> reservationList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reservation_dashboard);

        db = new DatabaseHelper(this);

        // Back button to staff_dashboard
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffReservationDashboardActivity.this, StaffDashboardActivity.class);
                startActivity(intent);
            }
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
        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            // Already on this screen
        });

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
        refreshReservationList();
    }
}
