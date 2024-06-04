package com.example.nursinghome_android.chatrealtime;

import androidx.appcompat.app.AppCompatActivity;

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
//        String userId = "bacsi1"; // Your ID as a user.
//        String userName = " Bs Dũng"; // You name as a user.
//        String userAvatar ="https://afamilycdn.com/150157425591193600/2023/2/24/ltt5998090128-01-16770559389571071835014-1677222250874-16772222511521048969129.jpg"; // The image you set as the user avatar must be network image. e.g., https://storage.zego.im/IMKit/avatar/avatar-0.png
//        connectUser(userId, userName,userAvatar);

        String userId = "duong123"; // Your ID as a user.
        String userName = "Quang Dương"; // You name as a user.
        String userAvatar ="http://res.cloudinary.com/djq4zsauv/image/upload/v1712820969/uep2jhlyf4ykelacyngh.png";
        connectUser(userId, userName,userAvatar);


//        String userId = "ddhuyeen"; // Your ID as a user.
//        String userName = "ĐD Huyền"; // You name as a user.
//        String userAvatar ="https://res.cloudinary.com/djq4zsauv/image/upload/v1714842868/otp8siickyzt5gcnppga.png";
//        connectUser(userId, userName,userAvatar);

        String appSign ="ed672560f0c6413248f865b7adf9b5bc15c871f6f6a63cfb8bdb4254bd8f484f";  // yourAppSign
        Long appID = 949308766L;   // yourAppID

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