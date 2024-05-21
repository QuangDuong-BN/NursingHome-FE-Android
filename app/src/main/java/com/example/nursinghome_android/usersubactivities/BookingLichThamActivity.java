package com.example.nursinghome_android.usersubactivities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.nursinghome_android.FragmentDatLichTham.ViewPagerAdapter1;
import com.example.nursinghome_android.R;
import com.google.android.material.tabs.TabLayout;

public class BookingLichThamActivity extends AppCompatActivity {
    private Toolbar toolbar;
//    private CalendarView datePicker;
//    private Spinner timeSpinner;
//    private Button registerButton;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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
            Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            actionBar.setHomeAsUpIndicator(upArrow);
        }

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        tabLayout = findViewById(R.id.tableLayoutBookingLichTham);
        viewPager = findViewById(R.id.viewPageerBookingLichTham);
        ViewPagerAdapter1 viewPagerAdapter1 = new ViewPagerAdapter1(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter1);
        tabLayout.setupWithViewPager(viewPager);
//        datePicker = findViewById(R.id.datePicker);
//        timeSpinner = findViewById(R.id.timeSpinner);
//        registerButton = findViewById(R.id.registerButton);
//
//        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
//                R.array.time_entries, android.R.layout.simple_spinner_item);
//        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        timeSpinner.setAdapter(timeAdapter);
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerAppointment();
//            }
//        });
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

    }

}