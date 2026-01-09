package com.example.androiduidesignlab;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomerReservationMakingActivity extends AppCompatActivity {

    private TextView tvSelectedDate;
    private Button btnChangeDate;
    private EditText etNumberOfGuests;
    private long reservationId = -1;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_reservation_making);

        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnChangeDate = findViewById(R.id.btnChangeDate);
        etNumberOfGuests = findViewById(R.id.etNumberOfGuests);

        reservationId = getIntent().getLongExtra("reservation_id", -1);
        String date = getIntent().getStringExtra("date");

        if (date != null) {
            selectedDate = date;
        } else {
            final Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = sdf.format(c.getTime());
        }
        tvSelectedDate.setText(selectedDate);

        btnChangeDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                        tvSelectedDate.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        View.OnClickListener availableTimesClickListener = v -> {
            TextView timeTextView = (TextView) ((LinearLayout) v).getChildAt(0);
            String time = timeTextView.getText().toString();
            
            String guestsStr = etNumberOfGuests.getText().toString();
            if (guestsStr.isEmpty()) {
                Toast.makeText(this, "Please enter the number of guests", Toast.LENGTH_SHORT).show();
                return;
            }
            int guests = Integer.parseInt(guestsStr);

            Intent intent = new Intent(CustomerReservationMakingActivity.this, CustomerReservationFinalisationActivity.class);
            intent.putExtra("date", selectedDate);
            intent.putExtra("time", time);
            intent.putExtra("guests", guests);
            intent.putExtra("reservation_id", reservationId);
            startActivity(intent);
        };

        findViewById(R.id.availableTimes1).setOnClickListener(availableTimesClickListener);
        findViewById(R.id.availableTimes2).setOnClickListener(availableTimesClickListener);

        findViewById(R.id.btnReservations).setOnClickListener(v -> startActivity(new Intent(this, CustomerReservationDashboardActivity.class)));
        findViewById(R.id.btnMenu).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v -> startActivity(new Intent(this, CustomerSettingsActivity.class)));
    }
}
