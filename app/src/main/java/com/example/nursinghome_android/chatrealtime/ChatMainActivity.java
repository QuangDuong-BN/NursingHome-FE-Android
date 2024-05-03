package com.example.nursinghome_android.chatrealtime;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nursinghome_android.R;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.zimkit.services.ZIMKit;


public class ChatMainActivity extends AppCompatActivity {
    public static ChatMainActivity sInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        sInstance = this;
        Long appId = 1643629424L;    // The AppID you get from ZEGOCLOUD Admin Console.
        String appSign = "f648f9982a76de835b9a988cad8621e4c88a7a6cdf9bf36694dfb55bd684fe6c";    // The App Sign you get from ZEGOCLOUD Admin Console.
        ZIMKit.initWith(getApplication(), appId, appSign);
        // Online notification for the initialization (use the following code if this is needed).
        ZIMKit.initNotifications();

        startActivity(new Intent(this, MyZIMKitActivity.class));
        finish();
    }
}