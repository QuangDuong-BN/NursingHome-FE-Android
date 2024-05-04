package com.example.nursinghome_android.chatrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.example.nursinghome_android.R;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.zimkit.common.ZIMKitRouter;
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType;
import com.zegocloud.zimkit.components.message.interfaces.ZIMKitMessagesListListener;
import com.zegocloud.zimkit.components.message.model.ZIMKitHeaderBar;
import com.zegocloud.zimkit.components.message.ui.ZIMKitMessageFragment;
import com.zegocloud.zimkit.services.ZIMKit;
import com.zegocloud.zimkit.services.config.InputConfig;

import im.zego.zim.enums.ZIMConversationType;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Application application = getApplication(); // Android's application context

        Long appID = 949308766L;   // yourAppID
        String appSign ="ed672560f0c6413248f865b7adf9b5bc15c871f6f6a63cfb8bdb4254bd8f484f";  // yourAppSign
        String userID ="duong123"; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName ="Quang Duong";   // yourUserName

//        String userID = "bacsi1"; // Your ID as a user.
//        String userName = " Bs Dũng"; // You name as a user.

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName, callInvitationConfig);
        ZIMKit.registerMessageListListener(new ZIMKitMessagesListListener() {
            @Override
            public ZIMKitHeaderBar getMessageListHeaderBar(ZIMKitMessageFragment fragment) {
                if (fragment != null) {
                    // Need to add a custom header bar in one-to-one chat
                    if (fragment.getConversationType() == ZIMConversationType.PEER) {
                        // Get conversationID and conversationName from the fragment
                        String conversationID = fragment.getConversationID(); //The ID of the user you want to call.
                        String conversationName = fragment.getConversationName(); //The username of the user you want to call.
                        // Create a call button
                        ZegoSendCallButton sendCallButton = new ZegoSendCallButton(getApplicationContext(), conversationID, conversationName);
                        // Create a header bar and set the sendCallButton to the 'rightView'
                        ZIMKitHeaderBar headerBar = new ZIMKitHeaderBar();
                        headerBar.setRightView(sendCallButton);
                        return headerBar;
                    }
                }
                return null;
            }
        });

        startSingleChat("bacsi1");
    }
    private void startSingleChat(String userId){

        ZIMKitRouter.toMessageActivity(this, userId, ZIMKitConversationType.ZIMKitConversationTypePeer);
        finish();
    }
}