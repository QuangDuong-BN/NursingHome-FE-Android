package com.example.nursinghome_android.ListUserActivity;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.ListViewSetUp.DropdownAdapterUser;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.config.LoadingDialog;
import com.example.nursinghome_android.entityDTO.UserItemforListUserDTO;
import com.example.nursinghome_android.usersubactivities.AddUser2Activity;
import com.example.nursinghome_android.usersubactivities.BookingLichThamActivity;
import com.example.nursinghome_android.usersubactivities.CaiDatThongTinNguoiThanActivity;
import com.example.nursinghome_android.usersubactivities.ChooseHealtyOrMealPlan;
import com.example.nursinghome_android.usersubactivities.Healthactivity;
import com.example.nursinghome_android.usersubactivities.ListServiceInfoActivity;
import com.example.nursinghome_android.usersubactivities.MealPlanActivity;
import com.example.nursinghome_android.valueStatic.BookingInfo;
import com.example.nursinghome_android.valueStatic.ChooseFuture;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class ListUserActivity extends AppCompatActivity implements DropdownAdapterUser.OnClickListener {
    private ListView mDropdownListView;
    private DropdownAdapterUser mAdapter;
    private List<UserItemforListUserDTO> mDropdownItems = new ArrayList<>();
    ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        toolbar = findViewById(R.id.toolbarListUser);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            actionBar.setHomeAsUpIndicator(upArrow);
        }
        mDropdownListView = findViewById(R.id.listViewDropdownUser);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListUserActivity.this, AddUser2Activity.class);
                startActivity(intent);
            }
        });

        LoadingDialog loadingDialog = new LoadingDialog(ListUserActivity.this);
        loadingDialog.startLoadingDialog();
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/user/get_list_user_by_family_member")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    String result = response.body().string();
                    mDropdownItems = parseJsonToUserList(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new DropdownAdapterUser(ListUserActivity.this, mDropdownItems, ListUserActivity.this);
                            mDropdownListView.setAdapter(mAdapter);
//                            Toasty.success(ListUserActivity.this, result, Toasty.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(ListUserActivity.this, "Kết nối thất bại", Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });
        loadingDialog.dismissDialog();
    }

    @Override
    public void onItemClick(Long id) {
        // Xử lý sự kiện khi một mục được nhấn
//        Toasty.info(this, "Clicked on item: " + id, Toasty.LENGTH_SHORT).show();
        if (ChooseFuture.chooseFuture.equals("BookingService")) {
            BookingInfo.userIdFk = id;
            Intent intent = new Intent(ListUserActivity.this, ListServiceInfoActivity.class);
            startActivity(intent);
        }
        if (ChooseFuture.chooseFuture.equals("BookingLichTham")) {
            BookingInfo.userIdFk = id;
            Intent intent = new Intent(ListUserActivity.this, BookingLichThamActivity.class);
            startActivity(intent);
        }
        if (ChooseFuture.chooseFuture.equals("MealPlan")) {
            BookingInfo.userIdFk = id;
            Intent intent = new Intent(ListUserActivity.this, MealPlanActivity.class);
            startActivity(intent);
        }

        if (ChooseFuture.chooseFuture.equals("health")) {
            BookingInfo.userIdFk = id;
            Intent intent = new Intent(ListUserActivity.this, ChooseHealtyOrMealPlan.class);
            startActivity(intent);
        }
        if (ChooseFuture.chooseFuture.equals("CaiDatThongTinNguoiThan")) {
            BookingInfo.userIdFk = id;
            Intent intent = new Intent(ListUserActivity.this, CaiDatThongTinNguoiThanActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<UserItemforListUserDTO> parseJsonToUserList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<UserItemforListUserDTO>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }
}