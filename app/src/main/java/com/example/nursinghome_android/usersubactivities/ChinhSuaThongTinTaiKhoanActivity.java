package com.example.nursinghome_android.usersubactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.nursinghome_android.R;

import java.util.Arrays;
import java.util.Calendar;

public class ChinhSuaThongTinTaiKhoanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin_tai_khoan);

        Toolbar toolbar = findViewById(R.id.toolbarChinhSuaThongTinTaiKhoan);
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
        Spinner spinnerAddress = findViewById(R.id.spinnerRelationshipChinhSuaThongTinTaiKhoan);

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
        final EditText editTextDateOfBirth = findViewById(R.id.editTextDateOfBirthChinhSuaThongTinTaiKhoan);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChinhSuaThongTinTaiKhoanActivity.this,
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

        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGenderChinhSuaThongTinTaiKhoan);
        radioGroupGender.check(R.id.radioButtonMaleCaiDatThongTinNguoiThan);
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