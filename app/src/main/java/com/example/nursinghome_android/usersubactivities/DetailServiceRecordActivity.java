package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.MainMainActivity;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.ServiceRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailServiceRecordActivity extends AppCompatActivity {
    SharedPreferences prefs;
    String token;
    Request request;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service_record);

        Toolbar toolbar = findViewById(R.id.toolbarDetailServiceRecord);
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

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        request = new Request.Builder()
                .url(baseURL + "/service_record/get_by_id?id=" + ServiceRecord.id)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        // Tạo OkHttpClient
        client = new OkHttpClient();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    Object[] serviceRecord = parseJsonToListObject(myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tvMaHoaDon = findViewById(R.id.tvMaHoaDo2);
                            TextView tvDichVuDangKi = findViewById(R.id.tvDichVuDangKi2);
                            TextView tvNguoiThuHuong = findViewById(R.id.tvNguoiThuHuong2);
                            TextView tvThoiGianBatDau = findViewById(R.id.tvThoiGianBatDau2);
                            TextView tvThoiGianKetThuc = findViewById(R.id.tvThoiGianKetThuc2);
                            TextView tvChiPhi = findViewById(R.id.tvChiPhi2);
                            TextView tvTrangThaiThanhToan = findViewById(R.id.tvTrangThaiThanhToan2);
                            TextView tvMaGiuong = findViewById(R.id.tvMaGiuong2);
                            TextView tvMaPhong = findViewById(R.id.tvMaPhong2);
                            TextView tvViTriPhong = findViewById(R.id.tvViTriPhong2);

                            tvMaHoaDon.setText(StringToLong(serviceRecord[0].toString()));
                            tvDichVuDangKi.setText(serviceRecord[1].toString());
                            tvNguoiThuHuong.setText(serviceRecord[2].toString());
                            tvThoiGianBatDau.setText(convertToReadableTimeFormat(serviceRecord[3].toString()));
                            tvThoiGianKetThuc.setText(convertToReadableTimeFormat(serviceRecord[4].toString()));
                            tvChiPhi.setText(serviceRecord[8].toString());
                            tvTrangThaiThanhToan.setText(convertPaidAndUnPaid(serviceRecord[9].toString()));
                            tvMaGiuong.setText(serviceRecord[5].toString());
                            tvMaPhong.setText(serviceRecord[6].toString());
                            tvViTriPhong.setText(serviceRecord[7].toString());
                        }
                    });


                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

        Button btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailServiceRecordActivity.this, VNPayActivity.class);
                startActivity(intent);
            }
        });


        Button buttonRegisterService = findViewById(R.id.btnHuyDangKi);
        buttonRegisterService.setOnClickListener(v -> {

            // Tạo yêu cầu GET
            request = new Request.Builder()
                    .url(baseURL + "/service_record/update_record_status?id=" + ServiceRecord.id)
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            // Tạo OkHttpClient
            client = new OkHttpClient();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    DetailServiceRecordActivity.this.runOnUiThread(() -> {
                        SweetAlertDialog pDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Lỗi")
                                .setContentText("Có lỗi xảy ra, vui lòng thử lại sau")
                                .setConfirmText("Quay lại trang chủ!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        Intent intent = new Intent(DetailServiceRecordActivity.this, MainMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        pDialog.show();
                    });
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String myResponse = response.body().string();
                        DetailServiceRecordActivity.this.runOnUiThread(() -> {
                            if (myResponse.equals("success")) {
                                SweetAlertDialog pDialog = new SweetAlertDialog(DetailServiceRecordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thành công")
                                        .setContentText("Hủy đăng kí thành công")
                                        .setConfirmText("Quay lại trang chủ!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent intent = new Intent(DetailServiceRecordActivity.this, MainMainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                pDialog.show();
                            } else {
                                SweetAlertDialog pDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Lỗi")
                                        .setContentText("Có lỗi xảy ra, vui lòng thử lại sau")
                                        .setConfirmText("Quay lại trang chủ!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent intent = new Intent(DetailServiceRecordActivity.this, MainMainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                pDialog.show();

                            }
                        });
                    }
                }
            });

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

    public static Object[] parseJsonToListObject(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<Object[]>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }

    public String convertToReadableTimeFormat(String originalTime) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        SimpleDateFormat readableFormat = new SimpleDateFormat("hh:mm:ss a dd-MM-yyyy", Locale.US);
        try {
            Date date = originalFormat.parse(originalTime);
            return readableFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertPaidAndUnPaid(String status) {
        if (status.equals("PAID")) {
            return "Đã thanh toán";
        } else {
            return "Chưa thanh toán";
        }
    }

    public String StringToLong(String string) {
        Double doubleValue = Double.valueOf(string);
        Long longValue = doubleValue.longValue();
        return longValue.toString();
    }
}