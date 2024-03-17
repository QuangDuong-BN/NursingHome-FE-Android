package com.example.nursinghome_android.usersubactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nursinghome_android.R;

import es.dmoral.toasty.Toasty;

public class BookingLichThamActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DatePicker datePicker;
    private Spinner timeSpinner;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_service);

        toolbar = findViewById(R.id.toolbarBookinglichtham);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        datePicker = findViewById(R.id.datePicker);
        timeSpinner = findViewById(R.id.timeSpinner);
        registerButton = findViewById(R.id.registerButton);

        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_entries, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAppointment();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerAppointment() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String selectedTime = timeSpinner.getSelectedItem().toString();

        String message = "Bạn đã đăng ký lịch thăm bệnh vào ngày " + day + "/" + month + "/" + year + " vào buổi " + selectedTime;
        Toasty.success(this, message, Toast.LENGTH_LONG).show();
    }

}