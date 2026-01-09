package com.example.androiduidesignlab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomerReservationDashboardActivity extends AppCompatActivity implements CustomerReservationsAdapter.OnReservationListener {

    private RecyclerView rvReservations;
    private CustomerReservationsAdapter reservationsAdapter;
    private List<Reservation> reservationList;
    private DatabaseHelper db;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_dashboard);

        db = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        currentUsername = prefs.getString("username", null);

        rvReservations = findViewById(R.id.rvReservations);
        rvReservations.setLayoutManager(new LinearLayoutManager(this));

        reservationList = new ArrayList<>();
        reservationsAdapter = new CustomerReservationsAdapter(reservationList, this);
        rvReservations.setAdapter(reservationsAdapter);

        FloatingActionButton fabAddReservation = findViewById(R.id.fabAddReservation);
        fabAddReservation.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerReservationDashboardActivity.this, CustomerReservationMakingActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnReservations).setOnClickListener(v -> {});
        findViewById(R.id.btnMenu).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v -> startActivity(new Intent(this, CustomerSettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshReservationList();
    }

    private void refreshReservationList() {
        reservationList.clear();
        if (currentUsername != null) {
            reservationList.addAll(db.getReservationsForUser(currentUsername));
        }
        reservationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClick(long id, String date, String time, int guests, String details) {
        Intent intent = new Intent(this, CustomerReservationMakingActivity.class);
        intent.putExtra("reservation_id", id);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("guests", guests);
        intent.putExtra("details", details);
        startActivity(intent);
    }

    @Override
    public void onCancelClick(long id) {
        db.deleteReservation(id);
        refreshReservationList();
    }
}
