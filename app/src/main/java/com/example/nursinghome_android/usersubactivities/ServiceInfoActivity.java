package com.example.nursinghome_android.usersubactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.mainactivity.UserActivity;

public class ServiceInfoActivity extends AppCompatActivity {
    ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        buttonBack = findViewById(R.id.imageViewBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceInfoActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });
    }
}