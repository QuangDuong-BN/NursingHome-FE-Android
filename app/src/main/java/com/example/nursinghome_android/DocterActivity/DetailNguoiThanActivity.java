package com.example.nursinghome_android.DocterActivity;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.chatrealtime.ConversationActivity;
import com.example.nursinghome_android.chatrealtime.MyZIMKitActivity;
import com.example.nursinghome_android.valueStatic.BookingInfo;
import com.example.nursinghome_android.valueStatic.ChatID;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailNguoiThanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nguoi_than);
        Toolbar toolbar = findViewById(R.id.toolbarDetailNguoiThan);
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

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/user/get_family_member_by_id?id=" + BookingInfo.userIdFk)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Get the response body as a string
                    String responseBody = response.body().string();

                    try {
                        // Parse the JSON response
                        JSONArray jsonArray = new JSONArray(responseBody);

                        // Iterate through the array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONArray innerArray = jsonArray.getJSONArray(i);

                            String name = innerArray.getString(0);
                            String location = innerArray.getString(1);
                            String phone = innerArray.getString(2);
                            String email = innerArray.getString(3);
                            String url = innerArray.getString(4);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView textViewName = findViewById(R.id.textViewName);
                                    textViewName.setText(name);
                                    TextView textViewLocation = findViewById(R.id.textViewAddress);
                                    textViewLocation.setText(location);
                                    TextView textViewPhone = findViewById(R.id.textViewPhoneNumber);
                                    textViewPhone.setText(phone);
                                    TextView textViewEmail = findViewById(R.id.textViewEmail);
                                    textViewEmail.setText(email);
                                    ImageView imageView = findViewById(R.id.imageFamilyUser);
                                    Glide.with(DetailNguoiThanActivity.this)
                                            .load(url)
                                            .apply(new RequestOptions().transform(new CenterCrop(), new CircleCrop()))
                                            .into(imageView);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });

        Button buttonCall = findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textViewEmail = findViewById(R.id.textViewEmail);
                String mail = textViewEmail.getText().toString();
                ChatID.chatID = mail;
                Intent intent = new Intent(DetailNguoiThanActivity.this, MyZIMKitActivity.class);
                startActivity(intent);
            }
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