package com.example.nursinghome_android.chatrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nursinghome_android.R;
import com.zegocloud.zimkit.common.ZIMKitRouter;
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        startSingleChat("bacsi1");
    }
    private void startSingleChat(String userId){
        ZIMKitRouter.toMessageActivity(this, userId, ZIMKitConversationType.ZIMKitConversationTypePeer);
        finish();
    }
}