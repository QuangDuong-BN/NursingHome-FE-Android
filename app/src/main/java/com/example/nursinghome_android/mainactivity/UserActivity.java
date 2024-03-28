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
import com.example.nursinghome_android.usersubactivities.MealPlanActivity;
import com.example.nursinghome_android.valueStatic.ChooseFuture;

public class UserActivity extends AppCompatActivity {
    Button buttonServiceInfo, buttonDatlichTham,buttonThongTinSucKhoe,buttonMealPlan;
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
        
        buttonDatlichTham = findViewById(R.id.buttonDatlichTham);
        buttonDatlichTham.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListUserActivity.class);
            ChooseFuture.chooseFuture= "BookingLichTham";
            startActivity(intent);
        });


        buttonSettingUser = findViewById(R.id.imageViewSetingUser);
        buttonSettingUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, SettingUserActivity.class);
            startActivity(intent);
        });

        buttonMealPlan = findViewById(R.id.buttonMealPlan);
        buttonMealPlan.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListUserActivity.class);
            ChooseFuture.chooseFuture= "MealPlan";
            startActivity(intent);
        });

        buttonThongTinSucKhoe = findViewById(R.id.buttonThongTinSucKhoe);
        buttonThongTinSucKhoe.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, ListUserActivity.class);
            ChooseFuture.chooseFuture= "health";
            startActivity(intent);
        });

    }
}