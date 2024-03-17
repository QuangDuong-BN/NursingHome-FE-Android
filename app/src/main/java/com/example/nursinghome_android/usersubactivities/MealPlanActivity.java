package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.BookingInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MealPlanActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private TextView mealsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        datePicker = findViewById(R.id.datePicker);
        mealsTextView = findViewById(R.id.mealsTextView);

        // Set sự kiện cho DatePicker
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth) -> updateMeals(year, monthOfYear, dayOfMonth));

        // Hiển thị thông tin về xuất ăn cho ngày hiện tại khi activity được tạo
        updateMeals(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
    }

    private void updateMeals(int year, int month, int day) {
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
        mealsTextView.setText(mealsText);
    }

}