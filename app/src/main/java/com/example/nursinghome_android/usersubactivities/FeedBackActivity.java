package com.example.nursinghome_android.usersubactivities;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.UserInfoStatic;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;

public class FeedBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Toolbar toolbar = findViewById(R.id.toolbarFeedback);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            actionBar.setHomeAsUpIndicator(upArrow);
        }

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));


        SharedPreferences prefs1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String name = prefs1.getString("name", "null");
        TextInputEditText nameUser = findViewById(R.id.editTextNameFromFeedback);
        nameUser.setText(name);

        EditText comment = findViewById(R.id.commentEditText);
        Button sendButton = findViewById(R.id.submitFeedback);
        sendButton.setOnClickListener(v -> {
            String commentText = comment.getText().toString();
            Toasty.info(getApplicationContext(), commentText).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}