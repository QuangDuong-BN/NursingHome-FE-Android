package com.example.nursinghome_android.DocterActivity;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.nursinghome_android.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DocterActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_action);
        Toolbar toolbar = findViewById(R.id.toolbarDocterAction);
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
        Locale locale = new Locale("vi");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        CalendarView datePicker;
        // Inflate the layout for this fragment
        datePicker = findViewById(R.id.datePickerActionFrament);
        // Lấy thời gian hiện tại từ datePicker
        long currentTimeMillis = datePicker.getDate();

        // Chuyển đổi thời gian thành đối tượng Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);

        // Định dạng ngày tháng năm thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());
        TextView tvdateOfAction = findViewById(R.id.tvdateOfAction);
        tvdateOfAction.setText("Hoạt động ngày: " + dateString);

        callApiSetTextActionFragment(dateString);

        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", (month + 1)) + "-" + dayOfMonth;
            CardView cardViewMorning = findViewById(R.id.cardViewMorning);
            CardView cardViewAfternoon = findViewById(R.id.cardViewAfternoon);
            cardViewMorning.setVisibility(CardView.INVISIBLE);
            cardViewAfternoon.setVisibility(CardView.INVISIBLE);

            new Handler().postDelayed(() -> {
                cardViewMorning.setVisibility(CardView.VISIBLE);
                cardViewAfternoon.setVisibility(CardView.VISIBLE);
            }, 100);

            tvdateOfAction.setText("Hoạt động ngày: " + date);
            callApiSetTextActionFragment(date);

        });
    }

    public void callApiSetTextActionFragment(String dateOfAction) {
        // ket noi voi server, lay thong tin cua nguoi than


        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/action/get?dateOfAction=" + dateOfAction)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        // Thực hiện yêu cầu bất đồng bộ
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    List<Object[]> object = parseJsonToServiceInfoList(myResponse);

                    runOnUiThread(() -> {
                        // do something...
                        TextView tvActionMorning = findViewById(R.id.tvActionMorning);
                        TextView tvIsVistAbleMorning = findViewById(R.id.tvIsVistAbleMorning);
                        TextView tvActionAfternoon = findViewById(R.id.tvActionAfternoon);
                        TextView tvIsVistAbleAfternoon = findViewById(R.id.tvIsVistAbleAfternoon);
                        if (object.size() == 0) {
                            tvActionMorning.setText("Không có hoạt động nào");
                            tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                            tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));

                            tvActionAfternoon.setText("Không có hoạt động nào");
                            tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                            tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));

                        }

                        if (object.size() == 1) {
                            if (object.get(0)[3].equals("MORNING")) {
                                tvActionMorning.setText(object.get(0)[0].toString() + ":\n" + object.get(0)[1].toString());
                                if (object.get(0)[4].toString().equals("false")) {
                                    tvIsVistAbleMorning.setText("Lưu ý: Không thể đặt lịch thăm");
                                    tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.red));
                                } else {
                                    tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                                    tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));
                                }

                                tvActionAfternoon.setText("Không có hoạt động nào");
                                tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));


                            } else {
                                tvActionAfternoon.setText(object.get(0)[0].toString() + ":\n" + object.get(0)[1].toString());
                                if (object.get(0)[4].toString().equals("false")) {
                                    tvIsVistAbleAfternoon.setText("Lưu ý: Không thể đặt lịch thăm");
                                    tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.red));
                                } else {
                                    tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                                    tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));
                                }

                                tvActionMorning.setText("Không có hoạt động nào");
                                tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));
                            }
                        }
                        if (object.size() == 2) {
                            tvActionMorning.setText(object.get(0)[0].toString() + ":\n" + object.get(0)[1].toString());
                            if (object.get(0)[4].toString().equals("false")) {
                                tvIsVistAbleMorning.setText("Lưu ý: Không thể đặt lịch thăm");
                                tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.red));
                            } else {
                                tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));
                            }
                            tvActionAfternoon.setText(object.get(1)[0].toString() + ":\n" + object.get(1)[1].toString());
                            if (object.get(1)[4].toString().equals("false")) {
                                tvIsVistAbleAfternoon.setText("Lưu ý: Không thể đặt lich thăm");
                                tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.red));
                            } else {
                                tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));
                            }
                        }

                    });
                }
            }
        });
    }

    public static List<Object[]> parseJsonToServiceInfoList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
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