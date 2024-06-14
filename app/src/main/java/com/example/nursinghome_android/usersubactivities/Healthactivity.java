package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.BookingInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Healthactivity extends AppCompatActivity {
    private TextView tvServiceName, tvId, tvAddress, tvDate;
    private TextView tvWeight, tvBloodPressure, tvHeartRate, tvTemperature, tvAwareness, tvEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthactivity);

        Toolbar toolbar = findViewById(R.id.toolbarHealth);
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

        tvServiceName = findViewById(R.id.tvServiceName);
        tvId = findViewById(R.id.tvid);
        tvAddress = findViewById(R.id.tvAddress);
        tvDate = findViewById(R.id.tvDate);
        tvWeight = findViewById(R.id.tvWeight);
        tvBloodPressure = findViewById(R.id.tvBloodPressure);
        tvHeartRate = findViewById(R.id.tvHeartRate);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvAwareness = findViewById(R.id.tvAwareness);
        tvEmotion = findViewById(R.id.tvEmotion);

        tvServiceName.setText("NULL");
        tvId.setText("NULL");
        tvAddress.setText("NULL");
        tvDate.setText("NULL");
        tvWeight.setText("NULL");
        tvBloodPressure.setText("NULL");
        tvHeartRate.setText("NULL");
        tvTemperature.setText("NULL");
        tvAwareness.setText("NULL");
        tvEmotion.setText("NULL");

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);


        Request request = new Request.Builder()
                .url(baseURL + "/health_record_info/get_by_id?id=" + BookingInfo.userIdFk)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        // Parse JSON response
                        JSONArray jsonArray = new JSONArray(responseBody);
                        if (jsonArray.length() > 0) {
                            JSONObject mealPlan = jsonArray.getJSONObject(0);
                            final String userId = mealPlan.getString("userId");
                            final String address = mealPlan.getString("address");
                            final String name = mealPlan.getString("name");
                            final double weight = mealPlan.getDouble("weight");
                            final String imageUrl = mealPlan.getString("image_url");
                            final int bloodPressure = mealPlan.getInt("bloodPressure");
                            final int heartbeat = mealPlan.getInt("heartbeat");
                            final double temperature = mealPlan.getDouble("temperature");
                            final String awareness = mealPlan.getString("awareness");
                            final String mood = mealPlan.getString("mood");
                            String date = mealPlan.getString("date");
                            final String note = mealPlan.getString("note");
                            try {
                                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateObj = originalFormat.parse(date);
                                date = targetFormat.format(dateObj);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            // Update UI on the main thread
                            String finalDate = date;
                            runOnUiThread(() -> {
                                tvServiceName = findViewById(R.id.tvServiceName);
                                tvId = findViewById(R.id.tvid);
                                tvAddress = findViewById(R.id.tvAddress);
                                tvDate = findViewById(R.id.tvDate);
                                tvWeight = findViewById(R.id.tvWeight);
                                tvBloodPressure = findViewById(R.id.tvBloodPressure);
                                tvHeartRate = findViewById(R.id.tvHeartRate);
                                tvTemperature = findViewById(R.id.tvTemperature);
                                tvAwareness = findViewById(R.id.tvAwareness);
                                tvEmotion = findViewById(R.id.tvEmotion);

                                tvServiceName.setText(name);
                                tvId.setText(userId);
                                tvAddress.setText(address);
                                tvDate.setText(finalDate);
                                tvWeight.setText(weight + " kg");
                                tvBloodPressure.setText(bloodPressure + " mmHg");
                                tvHeartRate.setText(heartbeat + " nhịp/phút");
                                tvTemperature.setText(temperature + " độ C");
                                tvAwareness.setText(awareness);
                                tvEmotion.setText(mood);

                                ImageView imageView = findViewById(R.id.imageViewIcon);
                                Glide.with(Healthactivity.this)
                                        .load(imageUrl)
                                        .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                                        .into(imageView);

                            });
                        } else {
                            runOnUiThread(() -> {
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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