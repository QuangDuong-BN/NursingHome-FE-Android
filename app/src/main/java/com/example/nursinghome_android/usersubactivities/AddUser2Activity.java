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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddUser2Activity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user2);

        toolbar = findViewById(R.id.toolbarAddUser2);
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

        Spinner spinnerAddress = findViewById(R.id.spinnerAddress);

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

        // Find the EditText in your layout
        final EditText editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddUser2Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Display the selected date in the EditText

                                String formattedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                                editTextDateOfBirth.setText(formattedDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Button buttonAddUser2 = findViewById(R.id.buttonAddUser2);
        buttonAddUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the input fields
                String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
                String dateOfBirth = ((EditText) findViewById(R.id.editTextDateOfBirth)).getText().toString();
                String address = ((Spinner) findViewById(R.id.spinnerAddress)).getSelectedItem().toString();
                String gender = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroupGender)).getCheckedRadioButtonId())).getText().toString().equals("Ông") ? "MALE" : "FEMALE";

                // Create a JSON object with these values
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("dateOfBirth", dateOfBirth);
                    jsonObject.put("address", address);
                    jsonObject.put("gender", gender);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String token = prefs.getString("token", null);

                // Create a new OkHttpClient instance
                OkHttpClient client = new OkHttpClient();

                // Create a new RequestBody with the JSON object and set the media type to JSON
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

                // Create a new Request with the API URL and the RequestBody
                Request request = new Request.Builder()
                        .url(baseURL + "/user/register_for_family_member")
                        .addHeader("Authorization", "Bearer " + token)
                        .post(body)
                        .build();

                // Call the API using the OkHttpClient and handle the response
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            Toasty.success(AddUser2Activity.this, "Thêm thành viên gia đình thành công", Toasty.LENGTH_SHORT).show();
                            throw new IOException("Unexpected code " + response);
                        } else {
                            // Handle the response
                        }
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
}