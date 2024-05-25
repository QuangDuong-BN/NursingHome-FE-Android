package com.example.nursinghome_android.usersubactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.nursinghome_android.R;

public class DetailServiceRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service_record);

        Toolbar toolbar = findViewById(R.id.toolbarHealth);
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
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}