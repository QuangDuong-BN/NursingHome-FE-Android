package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.entityDTO.UserItemforListUserDTO;
import com.example.nursinghome_android.valueStatic.BookingInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CaiDatThongTinNguoiThanActivity extends AppCompatActivity {
    private List<UserItemforListUserDTO> mDropdownItems = new ArrayList<>();
    String result;
    Object[] object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat_thong_tin_nguoi_than);

        Toolbar toolbar = findViewById(R.id.toolbarCaiDatThongTinNguoiThan);
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
        Spinner spinnerAddress = findViewById(R.id.spinnerRelationshipCaiDatThongTinNguoiThan);


        // ket noi voi server, lay thong tin cua nguoi than
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/user/get_user_by_id?id=" + BookingInfo.userIdFk)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        // Thực hiện yêu cầu bất đồng bộ
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Xử lý khi gặp lỗi
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(CaiDatThongTinNguoiThanActivity.this, "Kết nối với máy chủ thất bại! ", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    result = response.body().string();
                    object = parseJsonToUserList(result);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toasty.success(CaiDatThongTinNguoiThanActivity.this, "Lấy thông tin người thân thành công!", Toast.LENGTH_SHORT).show();
                            Toasty.info(CaiDatThongTinNguoiThanActivity.this, result, Toast.LENGTH_SHORT).show();
                            Toasty.info(CaiDatThongTinNguoiThanActivity.this, object[0].toString(), Toast.LENGTH_SHORT).show();
                            EditText editTextName = findViewById(R.id.editTextNameCaiDatThongTinNguoiThan);
                            editTextName.setText(object[0].toString());

                            EditText editTextDateOfBirth = findViewById(R.id.editTextDateOfBirthCaiDatThongTinNguoiThan);
                            editTextDateOfBirth.setText(object[1].toString());

                            // Danh sách các tỉnh ở Việt Nam
                            String[] provinces = new String[]{
                                    "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh",
                                    "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng",
                                    "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang",
                                    "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa",
                                    "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An",
                                    "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam",
                                    "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình",
                                    "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang",
                                    "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên", "Cần Thơ", "Đà Nẵng", "Hải Phòng",
                                    "Hà Nội", "TP HCM"
                            };

                            // Tạo một ArrayAdapter sử dụng mảng string và layout spinner mặc định
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(CaiDatThongTinNguoiThanActivity.this, android.R.layout.simple_spinner_item, provinces);

                            // Chỉ định layout được sử dụng khi hiển thị danh sách các lựa chọn
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // Áp dụng adapter cho spinner
                            spinnerAddress.setAdapter(adapter);

                            // Tìm vị trí của "Hà Nội" trong mảng provinces
                            int defaultPosition = Arrays.asList(provinces).indexOf(object[2].toString());

                            // Thiết lập "Hà Nội" làm giá trị mặc định cho Spinner
                            spinnerAddress.setSelection(defaultPosition);
                            RadioGroup radioGroupGender = findViewById(R.id.radioGroupGenderCaiDatThongTinNguoiThan);
                            if (object[3].toString().equals("MALE")) {
                                radioGroupGender.check(R.id.radioButtonMaleCaiDatThongTinNguoiThan);
                            } else {
                                radioGroupGender.check(R.id.radioButtonFemaleCaiDatThongTinNguoiThan);
                            }

                        }
                    });
                }
            }

        });


        // Danh sách các tỉnh ở Việt Nam
        String[] provinces = new String[]{
                "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh",
                "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng",
                "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang",
                "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa",
                "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An",
                "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam",
                "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình",
                "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang",
                "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên", "Cần Thơ", "Đà Nẵng", "Hải Phòng",
                "Hà Nội", "TP HCM"
        };

        // Tạo một ArrayAdapter sử dụng mảng string và layout spinner mặc định
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinces);

        // Chỉ định layout được sử dụng khi hiển thị danh sách các lựa chọn
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Áp dụng adapter cho spinner
        spinnerAddress.setAdapter(adapter);

        // Tìm vị trí của "Hà Nội" trong mảng provinces
        int defaultPosition = Arrays.asList(provinces).indexOf("Hà Nội");

        // Thiết lập "Hà Nội" làm giá trị mặc định cho Spinner
        spinnerAddress.setSelection(defaultPosition);

        // Find the EditText in your layout
        final EditText editTextDateOfBirth = findViewById(R.id.editTextDateOfBirthCaiDatThongTinNguoiThan);

        // Set an OnClickListener for the EditText
        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                DatePickerDialog datePickerDialog = new DatePickerDialog(CaiDatThongTinNguoiThanActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Display the selected date in the EditText
                                editTextDateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGenderCaiDatThongTinNguoiThan);

        Button buttonSave = findViewById(R.id.buttonSaveCaiDatThongTinNguoiThan);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextName = findViewById(R.id.editTextNameCaiDatThongTinNguoiThan);
                EditText editTextDateOfBirth = findViewById(R.id.editTextDateOfBirthCaiDatThongTinNguoiThan);
                Spinner spinnerAddress = findViewById(R.id.spinnerRelationshipCaiDatThongTinNguoiThan);
                RadioGroup radioGroupGender = findViewById(R.id.radioGroupGenderCaiDatThongTinNguoiThan);
                String value = editTextName.getText().toString() + editTextDateOfBirth.getText().toString() + spinnerAddress.getSelectedItem().toString() + radioGroupGender.getCheckedRadioButtonId();
                Toasty.info(CaiDatThongTinNguoiThanActivity.this, value, Toast.LENGTH_SHORT).show();
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

    public static Object[] parseJsonToUserList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<Object[]>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }
}