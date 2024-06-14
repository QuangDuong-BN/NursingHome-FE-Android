package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.nursinghome_android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class MealPlanActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Calendar calendar;
    private TextView mealsTextView;
    CardView cardViewBreakfast, cardViewLunch, cardViewDinner;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        toolbar = findViewById(R.id.toolbarMealPlan);
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

        cardViewBreakfast = findViewById(R.id.cardViewBreakfast);
        cardViewLunch = findViewById(R.id.cardViewLunch);
        cardViewDinner = findViewById(R.id.cardViewDinner);
        calendarView = findViewById(R.id.datePickerActionFrament);

        calendarView = findViewById(R.id.datePickerActionFrament);
        // Lấy thời gian hiện tại từ datePicker
        long currentTimeMillis = calendarView.getDate();
        // Chuyển đổi thời gian thành đối tượng Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);

        // Định dạng ngày tháng năm thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());

        updateMeals(dateString);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-" + String.format("%02d", (month + 1)) + "-" + dayOfMonth;
                updateMeals(date);
            }
        });

    }

    private void updateMeals(String date) {
        cardViewBreakfast.setVisibility(CardView.INVISIBLE);
        cardViewLunch.setVisibility(CardView.INVISIBLE);
        cardViewDinner.setVisibility(CardView.INVISIBLE);


        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);


        Request request = new Request.Builder()
                .url(baseURL + "/meal_plan/get_by_date?date=" + date)
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
                            final String breakfast = mealPlan.getString("breakfast");
                            final String lunch = mealPlan.getString("lunch");
                            final String dinner = mealPlan.getString("dinner");

                            // Update UI on the main thread
                            runOnUiThread(() -> {
                                TextView breakfastTextView = findViewById(R.id.tvBreakfast);
                                TextView lunchTextView = findViewById(R.id.tvLunch);
                                TextView dinnerTextView = findViewById(R.id.tvDinner);

                                breakfastTextView.setText(breakfast);
                                lunchTextView.setText(lunch);
                                dinnerTextView.setText(dinner);
                            });
                        }
                        else {
                            runOnUiThread(() -> {
                                TextView breakfastTextView = findViewById(R.id.tvBreakfast);
                                TextView lunchTextView = findViewById(R.id.tvLunch);
                                TextView dinnerTextView = findViewById(R.id.tvDinner);

                                breakfastTextView.setText("Không có dữ liệu");
                                lunchTextView.setText("Không có dữ liệu");
                                dinnerTextView.setText("Không có dữ liệu");
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        new Handler().postDelayed(() -> {
            cardViewBreakfast.setVisibility(CardView.VISIBLE);
            cardViewLunch.setVisibility(CardView.VISIBLE);
            cardViewDinner.setVisibility(CardView.VISIBLE);
        }, 100);
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