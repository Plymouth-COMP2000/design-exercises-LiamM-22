package com.example.androiduidesignlab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class StaffReservationEditActivity extends AppCompatActivity {

    private EditText etReservationDetails;
    private Button btnSaveChanges;
    private DatabaseHelper db;
    private long reservationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservation_edit);

        db = new DatabaseHelper(this);
        etReservationDetails = findViewById(R.id.etReservationDetails);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        String reservationDetails = getIntent().getStringExtra("reservation_details");

        if (reservationDetails != null) {
            etReservationDetails.setText(reservationDetails);
        }

        btnSaveChanges.setOnClickListener(v -> {
            String updatedDetails = etReservationDetails.getText().toString();
            if (reservationId != -1) {
                db.updateReservation(new Reservation(reservationId, updatedDetails));
            }
            finish();
        });
    }
}
