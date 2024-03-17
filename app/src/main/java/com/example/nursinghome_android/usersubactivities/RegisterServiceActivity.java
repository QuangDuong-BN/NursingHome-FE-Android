package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.enumcustom.RoomType;
import com.example.nursinghome_android.valueStatic.BookingInfo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterServiceActivity extends AppCompatActivity {
    private Button buttonRegisterService;

    private Toolbar toolbar;
    TextInputEditText editTextProductionDate, editTextExpirationDate;
    private Spinner spinnerRoom, spinnerRoomType, spinnerBed;
    List<String> roomType = new ArrayList<>();
    List<String> roomNames = new ArrayList<>();
    List<String> bedNames = new ArrayList<>();
    HashMap<String, Long> roomNamesMap = new HashMap<>();
    HashMap<String, Long> bedNamesMap = new HashMap<>();

    String resultRoomfromServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);

        BookingInfo.roomType = null;
        BookingInfo.bedIdFk = null;
        BookingInfo.roomIdFk = null;
        BookingInfo.productionDate = null;
        BookingInfo.expirationDate = null;
        buttonRegisterService = findViewById(R.id.buttonRegisterService);
        spinnerRoom = findViewById(R.id.spinnerRoom);
        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        spinnerBed = findViewById(R.id.spinnerBed);

        toolbar = findViewById(R.id.toolbarRegisterService);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        // nut dang ki
        buttonRegisterService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerService();
            }
        });

        // Khởi tạo TextInputEditText
        editTextProductionDate = findViewById(R.id.editTextProductionDate);
        // Ngăn không cho bàn phím mở khi nhấn vào TextInputEditText
        editTextProductionDate.setShowSoftInputOnFocus(false);
        // Thiết lập OnClickListener

        editTextExpirationDate = findViewById(R.id.editTextExpirationDate);
        // Ngăn không cho bàn phím mở khi nhấn vào TextInputEditText
        editTextExpirationDate.setShowSoftInputOnFocus(false);

        editTextProductionDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                openDatePickerDialog1();
            }
        });

        editTextExpirationDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                openDatePickerDialog2();
            }
        });

        roomType = new ArrayList<>();
        roomType.add("Phòng 1 giường");
        roomType.add("Phòng 2 giường");
        roomType.add("Phòng 3 giường");

        // Tạo adapter và thiết lập cho Spinner
        ArrayAdapter<String> adapterRoomType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomType);
        adapterRoomType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(adapterRoomType);

        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy giá trị được chọn từ Spinner
                String selectedRoomType = (String) parentView.getItemAtPosition(position);
                if (selectedRoomType.equals("Phòng 1 giường")) {
                    BookingInfo.roomType = RoomType.ONE_BED;
                    setRoomFromServer(RoomType.ONE_BED);
                    setBedFromServer();
                } else if (selectedRoomType.equals("Phòng 2 giường")) {
                    BookingInfo.roomType = RoomType.TWO_BED;
                    setRoomFromServer(RoomType.TWO_BED);
                    setBedFromServer();
                } else if (selectedRoomType.equals("Phòng 3 giường")) {
                    BookingInfo.roomType = RoomType.THREE_BED;
                    setRoomFromServer(RoomType.THREE_BED);
                    setBedFromServer();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý sự kiện khi không có phần tử nào được chọn
            }
        });

//        setRoomFromServer(RoomType.ONE_BED);

        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy giá trị được chọn từ Spinner
                String selectedRoom = (String) parentView.getItemAtPosition(position);

                if (selectedRoom != null) {
                    BookingInfo.roomIdFk = roomNamesMap.get(selectedRoom);
                    setBedFromServer();
                }

                // Xử lý sự kiện khi một phần tử được chọn

                Toast.makeText(RegisterServiceActivity.this, "Bạn đã chọn phòng: " + selectedRoom, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý sự kiện khi không có phần tử nào được chọn

            }
        });

        // Tạo adapter và thiết lập cho Spinner
        ArrayAdapter<String> adapterBedNames = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bedNames);
        adapterBedNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBed.setAdapter(adapterBedNames);

        spinnerBed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy giá trị được chọn từ Spinner
                String selectedBed = (String) parentView.getItemAtPosition(position);
                if (selectedBed != null) {
                    BookingInfo.bedIdFk = bedNamesMap.get(selectedBed);
                }
                // Xử lý sự kiện khi một phần tử được chọn
                // Ví dụ: hiển thị thông báo
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý sự kiện khi không có phần tử nào được chọn
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

    private void openDatePickerDialog1() {
        // Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this,
                        (view, year1, monthOfYear, dayOfMonth) -> {
                            // Khi người dùng chọn ngày, cập nhật TextView với ngày được chọn
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year1, monthOfYear, dayOfMonth);
                            Date selectedDate = new Date(calendar.getTimeInMillis());
                            editTextProductionDate.setText(selectedDate.toString());
                            BookingInfo.productionDate = selectedDate;
                        }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void openDatePickerDialog2() {
        // Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this,
                        (view, year1, monthOfYear, dayOfMonth) -> {
                            // Khi người dùng chọn ngày, cập nhật TextView với ngày được chọn
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year1, monthOfYear, dayOfMonth);
                            Date selectedDate = new Date(calendar.getTimeInMillis());
                            editTextExpirationDate.setText(selectedDate.toString());
                            BookingInfo.expirationDate = selectedDate;
                        }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    // Phân tích JSON và trích xuất các giá trị "name"
    private List<String> parseRoomNames(String jsonData) {
        List<String> roomNames = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                Long id = jsonObject.getLong("id");
                roomNamesMap.put(name, id);
                roomNames.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roomNames;
    }

    private List<String> parseBedNames(String jsonData) {
        List<String> bedNames = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                Long id = jsonObject.getLong("id");
                bedNamesMap.put(name, id);
                bedNames.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bedNames;
    }


    public void setRoomFromServer(RoomType roomType) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/room/get_list_name_room_by_room_type?roomType=" + roomType)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    resultRoomfromServer = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultRoomfromServer != null) {
                                roomNames = parseRoomNames(resultRoomfromServer);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterServiceActivity.this, android.R.layout.simple_spinner_item, roomNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerRoom.setAdapter(adapter);
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterServiceActivity.this, "flase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setBedFromServer() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (BookingInfo.roomIdFk == null) {
            BookingInfo.bedIdFk = null;
            bedNames = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterServiceActivity.this, android.R.layout.simple_spinner_item, bedNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBed.setAdapter(adapter);
            return;
        }

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/bed/get_list_bed_by_room_id?id=" + BookingInfo.roomIdFk)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    String resultBedfromServer = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bedNames = parseBedNames(resultBedfromServer);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterServiceActivity.this, android.R.layout.simple_spinner_item, bedNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerBed.setAdapter(adapter);
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterServiceActivity.this, "flase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void registerService() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Toasty.info(RegisterServiceActivity.this,
                BookingInfo.roomIdFk + "\n" +
                        BookingInfo.bedIdFk + "\n" +
                        BookingInfo.productionDate + "\n" +
                        BookingInfo.expirationDate + "\n"
                , Toast.LENGTH_SHORT).show();
        String token = prefs.getString("token", null);
        if (BookingInfo.roomIdFk == null || BookingInfo.bedIdFk == null || BookingInfo.productionDate == null || BookingInfo.expirationDate == null) {
            Toast.makeText(RegisterServiceActivity.this, "Vui lòng chọn đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userIdFk", BookingInfo.userIdFk);
            jsonBody.put("roomIdFk", BookingInfo.roomIdFk);
            jsonBody.put("bedIdFk", BookingInfo.bedIdFk);
            jsonBody.put("productionDate", BookingInfo.productionDate);
            jsonBody.put("expirationDate", BookingInfo.expirationDate);
            jsonBody.put("roomType", BookingInfo.roomType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                jsonBody.toString());
        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/service_record/add")
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterServiceActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterServiceActivity.this, "flase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}