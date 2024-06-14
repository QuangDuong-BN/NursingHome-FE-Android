package com.example.nursinghome_android.chatrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.ChatID;
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

        SharedPreferences prefs1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String userId = prefs1.getString("email", "null");; // Your ID as a user.
        String userName = prefs1.getString("name", "null");
        String userAvatar =prefs1.getString("imageUrl", "null");

        String appSign ="6ff124e9cae0423d8d3e38af1edfe2a4240d35c1b3c21245277e712e4a8a0ed3";  // yourAppSign
        Long appID = 933237909L;   // yourAppID
//
        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userId, userName, callInvitationConfig);
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

        startSingleChat(ChatID.chatID);
    }
    private void startSingleChat(String userId){
        ZIMKitRouter.toMessageActivity(this, userId, ZIMKitConversationType.ZIMKitConversationTypePeer);
        finish();
    }
}