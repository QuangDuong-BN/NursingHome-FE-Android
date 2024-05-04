package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.ListViewSetUp.DropdownAdapterServiceInfo;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.config.LoadingDialog;
import com.example.nursinghome_android.entityDTO.ServiceInfoforListServiceInfoDTO;
import com.example.nursinghome_android.valueStatic.BookingInfo;
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

public class ListServiceInfoActivity extends AppCompatActivity implements DropdownAdapterServiceInfo.OnClickListener {
    ImageButton buttonBack;
    private ListView mDropdownListView;
    private DropdownAdapterServiceInfo mAdapter;
    private List<ServiceInfoforListServiceInfoDTO> mDropdownItems = new ArrayList<>();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service_info);

        toolbar = findViewById(R.id.toolbarListServiceInfo);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        LoadingDialog loadingDialog = new LoadingDialog(ListServiceInfoActivity.this);
        loadingDialog.startLoadingDialog();

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/service_info/get_all_for_list_service_info")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        mDropdownListView = findViewById(R.id.listViewDropdownServiceInfo);

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    String result = response.body().string();
                    mDropdownItems = parseJsonToServiceInfoList(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new DropdownAdapterServiceInfo(ListServiceInfoActivity.this, mDropdownItems, ListServiceInfoActivity.this);
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
                        Toasty.error(ListServiceInfoActivity.this, "Kết nối thất bại!", Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });
        loadingDialog.dismissDialog();
    }

    @Override
    public void onItemClick(Long id) {
        // Xử lý sự kiện khi một mục được nhấn
        BookingInfo.serviceInfoIdFk = id;
        Intent intent = new Intent(ListServiceInfoActivity.this, DetailServiceInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<ServiceInfoforListServiceInfoDTO> parseJsonToServiceInfoList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ServiceInfoforListServiceInfoDTO>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }
}