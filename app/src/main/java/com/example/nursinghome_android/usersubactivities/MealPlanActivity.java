package com.example.nursinghome_android.usersubactivities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.nursinghome_android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

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
        }

        cardViewBreakfast = findViewById(R.id.cardViewBreakfast);
        cardViewLunch = findViewById(R.id.cardViewLunch);
        cardViewDinner = findViewById(R.id.cardViewDinner);
        calendarView = findViewById(R.id.datePicker);

//        mealsTextView = findViewById(R.id.mealsTextView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                updateMeals(year, month, dayOfMonth);
                Toasty.success(MealPlanActivity.this, "Đã chọn ngày " + dayOfMonth + "/" + (month + 1) + "/" + year, Toasty.LENGTH_SHORT).show();
            }
        });

        // Get the current date
        Calendar currentCalendar = Calendar.getInstance();
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);

        // Update meals for the current date
        updateMeals(year, month, day);

    }

    private void updateMeals(int year, int month, int day) {
        cardViewBreakfast.setVisibility(CardView.INVISIBLE);
        cardViewLunch.setVisibility(CardView.INVISIBLE);
        cardViewDinner.setVisibility(CardView.INVISIBLE);

        // Tạo đối tượng Calendar và thiết lập ngày được chọn
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

//        // Tạo yêu cầu GET
//        Request request = new Request.Builder()
//                .url(baseURL + "/service_info/get_by_id?id=" + BookingInfo.serviceInfoIdFk)
//                .addHeader("Authorization", "Bearer " + token)
//                .build();
//
//        // Tạo OkHttpClient
//        OkHttpClient client = new OkHttpClient();
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                String result = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(result);
//                            Long id = jsonResponse.getLong("id");
//                            String name = jsonResponse.getString("name");
//                            String amenities = jsonResponse.getString("amenities");
//                            String nutritionMode = jsonResponse.getString("nutritionMode");
//                            String communityActivities = jsonResponse.getString("communityActivities");
//                            String careRegimen = jsonResponse.getString("careRegimen");
//                            String stringimageurl = jsonResponse.getString("imageUrlPrice");
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//            }
//        });

        // Format ngày thành chuỗi để hiển thị
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String selectedDate = dateFormat.format(calendar.getTime());

        // Tạo danh sách mục đồ ăn dựa trên ngày được chọn
        // Ở đây bạn có thể thay thế bằng mã để lấy dữ liệu từ nguồn dữ liệu thực tế của bạn

        // Ví dụ:
        // String breakfast = getBreakfast(selectedDate);
        // String lunch = getLunch(selectedDate);
        // String dinner = getDinner(selectedDate);

        // Hiển thị thông tin về xuất ăn trong TextView
        String mealsText = "Xuất ăn cho ngày " + selectedDate + ":\n"
                + "Sáng: Bánh mì, Trưa: Cơm gà, Tối: Canh chua";
//        mealsTextView.setText(mealsText);

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