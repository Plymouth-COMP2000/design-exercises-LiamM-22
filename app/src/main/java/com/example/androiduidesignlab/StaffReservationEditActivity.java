package com.example.androiduidesignlab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class StaffReservationEditActivity extends AppCompatActivity {

    private EditText etReservationDate;
    private EditText etReservationTime;
    private EditText etNumberOfGuests;
    private EditText etReservationDetails;
    private Button btnSaveChanges;
    private DatabaseHelper db;
    private long reservationId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservation_edit);

        db = new DatabaseHelper(this);
        etReservationDate = findViewById(R.id.etReservationDate);
        etReservationTime = findViewById(R.id.etReservationTime);
        etNumberOfGuests = findViewById(R.id.etNumberOfGuests);
        etReservationDetails = findViewById(R.id.etReservationDetails);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        username = getIntent().getStringExtra("username");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        int guests = getIntent().getIntExtra("guests", 1);
        String details = getIntent().getStringExtra("details");

        if (reservationId != -1) {
            etReservationDate.setText(date);
            etReservationTime.setText(time);
            etNumberOfGuests.setText(String.valueOf(guests));
            etReservationDetails.setText(details);
        }

        btnSaveChanges.setOnClickListener(v -> {
            String updatedDate = etReservationDate.getText().toString();
            String updatedTime = etReservationTime.getText().toString();
            int updatedGuests = Integer.parseInt(etNumberOfGuests.getText().toString());
            String updatedDetails = etReservationDetails.getText().toString();

            if (reservationId != -1) {
                db.updateReservation(new Reservation(reservationId, username, updatedDate, updatedTime, updatedGuests, updatedDetails));
            }
            finish();
        });

        findViewById(R.id.btnHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
