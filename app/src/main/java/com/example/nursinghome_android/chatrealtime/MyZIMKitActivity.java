package com.example.nursinghome_android.chatrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.nursinghome_android.R;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import im.zego.zim.enums.ZIMConversationType;
import im.zego.zim.enums.ZIMErrorCode;

import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.zimkit.components.message.interfaces.ZIMKitMessagesListListener;
import com.zegocloud.zimkit.components.message.model.ZIMKitHeaderBar;
import com.zegocloud.zimkit.components.message.ui.ZIMKitMessageFragment;
import com.zegocloud.zimkit.services.ZIMKit;
import com.zegocloud.zimkit.services.config.InputConfig;


public class MyZIMKitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputConfig inputConfig = new InputConfig();
        inputConfig.showVoiceButton = true;
        inputConfig.showEmojiButton = true;
        inputConfig.showAddButton = true;

        ZIMKit.setInputConfig(inputConfig);
        buttonClick();

    }

    public void buttonClick() {
        // userId and userName: 1 to 32 characters, can only contain digits, letters, and the following special characters: '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '=', '-', '`', ';', '’', ',', '.', '<', '>', '/', '\'


        SharedPreferences prefs1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String userId = prefs1.getString("email", "null");; // Your ID as a user.
        String userName = prefs1.getString("name", "null");
        String userAvatar =prefs1.getString("imageUrl", "null");
        connectUser(userId, userName,userAvatar);


//        String userId = "ddhuyeen"; // Your ID as a user.
//        String userName = "ĐD Huyền"; // You name as a user.
//        String userAvatar ="https://res.cloudinary.com/djq4zsauv/image/upload/v1714842868/otp8siickyzt5gcnppga.png";
//        connectUser(userId, userName,userAvatar);

        String appSign ="6ff124e9cae0423d8d3e38af1edfe2a4240d35c1b3c21245277e712e4a8a0ed3";  // yourAppSign
        Long appID = 933237909L;   // yourAppID

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userId, userName, callInvitationConfig);

    }


    public void connectUser(String userId, String userName,String userAvatar) {
        // Logs in.
        ZIMKit.connectUser(userId,userName,userAvatar, errorInfo -> {
            if (errorInfo.code == ZIMErrorCode.SUCCESS) {
                // Operation after successful login. You will be redirected to other modules only after successful login. In this sample code, you will be redirected to the conversation module.
                toConversationActivity();
            } else {
            }
        });
    }

    // Integrate the conversation list into your Activity as a Fragment
    private void toConversationActivity() {
        // Redirect to the conversation list (Activity) you created.
        Intent intent = new Intent(this,ConversationActivity.class);
        startActivity(intent);
        finish();
    }
}