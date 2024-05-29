package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.ListViewSetUp.DropdownAdapterLichSuDangKiDichVu;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.ServiceRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LichSuDangKiDichVu extends AppCompatActivity implements DropdownAdapterLichSuDangKiDichVu.OnClickListener {
    private ListView mDropdownListView;
    private DropdownAdapterLichSuDangKiDichVu mAdapter;
    private List<Object[]> mDropdownItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_dang_ki_dich_vu);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbarLichSuDangKiDichVu);
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
                .url(baseURL + "/service_record/get_all_by_id")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        mDropdownListView = findViewById(R.id.listViewDropdownServiceRecord);

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    String result = response.body().string();
                    mDropdownItems = parseJsonToListObject(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new DropdownAdapterLichSuDangKiDichVu(LichSuDangKiDichVu.this, mDropdownItems, LichSuDangKiDichVu.this);
                            mDropdownListView.setAdapter(mAdapter);
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(LichSuDangKiDichVu.this, "Kết nối thất bại!", Toasty.LENGTH_SHORT).show();
                    }
                });
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



    public static List<Object[]> parseJsonToListObject(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }

    @Override
    public void onItemClick(Long id) {
        ServiceRecord.id = id;
        Intent intent = new Intent(LichSuDangKiDichVu.this, DetailServiceRecordActivity.class);
        startActivity(intent);
    }

}