package com.example.androiduidesignlab;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CustomerReservationMakingActivity extends AppCompatActivity {

    private TextView tvSelectedDate;
    private Button btnChangeDate;
    private long reservationId = -1;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_making);

        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnChangeDate = findViewById(R.id.btnChangeDate);

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        String reservationDetails = getIntent().getStringExtra("reservation_details");

        if (reservationId != -1 && reservationDetails != null) {
            // Editing existing reservation - extract date and time
            // For now, we'll just show the whole string.
            selectedDate = reservationDetails;
        } else {
            // Creating new reservation - use today's date
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            selectedDate = day + "/" + (month + 1) + "/" + year;
        }
        tvSelectedDate.setText(selectedDate);

        btnChangeDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                        tvSelectedDate.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        View.OnClickListener availableTimesClickListener = v -> {
            Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerReservationFinalisationActivity.class);
            TextView tv = (TextView) ((View) v).findViewWithTag("time_text");
            String time = tv.getText().toString();
            intent.putExtra("reservation_details", selectedDate + " at " + time);
            intent.putExtra("reservation_id", reservationId);
            startActivity(intent);
        };

        findViewById(R.id.availableTimes1).setOnClickListener(availableTimesClickListener);
        findViewById(R.id.availableTimes2).setOnClickListener(availableTimesClickListener);

        // Bottom Navigation
        findViewById(R.id.btnReservations).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerReservationDashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationMakingActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerSettingsActivity.class);
            startActivity(intent);
        });
    }
}
