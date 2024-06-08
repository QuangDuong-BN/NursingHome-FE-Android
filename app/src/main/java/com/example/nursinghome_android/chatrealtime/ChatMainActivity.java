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
        Long appId = 933237909L;    // The AppID you get from ZEGOCLOUD Admin Console.
        String appSign = "6ff124e9cae0423d8d3e38af1edfe2a4240d35c1b3c21245277e712e4a8a0ed3";    // The App Sign you get from ZEGOCLOUD Admin Console.
        ZIMKit.initWith(getApplication(), appId, appSign);
        // Online notification for the initialization (use the following code if this is needed).
        ZIMKit.initNotifications();

        startActivity(new Intent(this, MyZIMKitActivity.class));
        finish();
    }
}