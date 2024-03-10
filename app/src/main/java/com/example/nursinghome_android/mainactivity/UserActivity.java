package com.example.nursinghome_android.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nursinghome_android.CustomUI.CustomButton;
import com.example.nursinghome_android.ListUserActivity.ListUserActivity;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.subactivities.SettingUserActivity;
import com.example.nursinghome_android.valueStatic.ChooseFuture;

public class UserActivity extends AppCompatActivity {
    Button buttonServiceInfo;
    ImageButton buttonSettingUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        buttonServiceInfo = findViewById(R.id.buttonServiceInfo);
        buttonServiceInfo.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListUserActivity.class);
            ChooseFuture.chooseFuture= "BookingService";
            startActivity(intent);
        });


        buttonSettingUser = findViewById(R.id.imageViewSetingUser);
        buttonSettingUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, SettingUserActivity.class);
            startActivity(intent);
        });

        CustomButton customButton = findViewById(R.id.customButton);
        customButton.setIcon(R.drawable.iconserviceinfo1);
        customButton.setLabel("Your Text");
    }
}