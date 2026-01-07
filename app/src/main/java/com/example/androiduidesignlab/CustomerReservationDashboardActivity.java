package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomerReservationDashboardActivity extends AppCompatActivity implements CustomerReservationsAdapter.OnReservationListener {

    private RecyclerView rvReservations;
    private CustomerReservationsAdapter reservationsAdapter;
    private List<Reservation> reservationList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_dashboard);

        db = new DatabaseHelper(this);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        rvReservations = findViewById(R.id.rvReservations);
        rvReservations.setLayoutManager(new LinearLayoutManager(this));

        reservationList = db.getAllReservations();
        reservationsAdapter = new CustomerReservationsAdapter(reservationList, this);
        rvReservations.setAdapter(reservationsAdapter);

        FloatingActionButton fabAddReservation = findViewById(R.id.fabAddReservation);
        fabAddReservation.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerReservationDashboardActivity.this, CustomerReservationMakingActivity.class);
            startActivity(intent);
        });

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            // Already on this screen
        });

        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationDashboardActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationDashboardActivity.this, CustomerSettingsActivity.class);
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
        Intent intent = new Intent(CustomerReservationDashboardActivity.this, CustomerReservationMakingActivity.class);
        intent.putExtra("reservation_id", id);
        intent.putExtra("reservation_details", details);
        startActivity(intent);
    }

    @Override
    public void onCancelClick(long id) {
        db.deleteReservation(id);
        refreshReservationList();
    }
}
