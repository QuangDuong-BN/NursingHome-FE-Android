package com.example.nursinghome_android.subactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.mainactivity.LoginActivity;
import com.example.nursinghome_android.mainactivity.UserActivity;

public class SettingUserActivity extends AppCompatActivity {
    Button buttonLogout;
    ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);
        buttonBack = findViewById(R.id.imageViewBackUserFromSetting);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(SettingUserActivity.this, UserActivity.class);
            startActivity(intent);
            finish();

        });

        buttonLogout = findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
            editor.putString("token", null);
            editor.apply();

            Intent intent = new Intent(SettingUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();


        });
    }
}