package com.example.nursinghome_android.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nursinghome_android.MainMainActivity;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.config.LoadingDialog;
import com.example.nursinghome_android.enumcustom.RoleUser;
import com.example.nursinghome_android.valueStatic.BaseURL;
import com.google.android.material.textfield.TextInputEditText;
import com.zegocloud.zimkit.services.ZIMKit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    String baseURL, token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences prefs1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();

        Long appId = 933237909L;    // The AppID you get from ZEGOCLOUD Admin Console.
        String appSign = "6ff124e9cae0423d8d3e38af1edfe2a4240d35c1b3c21245277e712e4a8a0ed3";    // The App Sign you get from ZEGOCLOUD Admin Console.
        ZIMKit.initWith(getApplication(), appId, appSign);
        // Online notification for the initialization (use the following code if this is needed).
        ZIMKit.initNotifications();

        baseURL = BaseURL.baseURL;
        //baseURL = "http://192.168.43.167:8080";
        editor.putString("baseURL", baseURL);
        editor.apply();
        token = prefs1.getString("token", null);

        // check thong tin dang nhap
        checkTokenAndRoleUser();


        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.editTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);


        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> {
            login();

        });
    }


    private void checkTokenAndRoleUser() {
        LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);
        loadingDialog.startLoadingDialog();

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token != null) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(baseURL + "/user/get_user")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    loadingDialog.dismissDialog();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    loadingDialog.dismissDialog();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        RoleUser roleUser = RoleUser.valueOf(jsonResponse.getString("role"));
                        if (roleUser.equals(RoleUser.FAMILY_MEMBER)) {
                            Intent intent = new Intent(LoginActivity.this, MainMainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (roleUser.equals(RoleUser.DOCTOR) || roleUser.equals(RoleUser.NURSE)) {
                            Intent intent = new Intent(LoginActivity.this, DocterAndNurseActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (roleUser.equals(RoleUser.ADMIN)) {
                            Intent intent = new Intent(LoginActivity.this, MainMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Xử lý khi có lỗi xảy ra trong việc xử lý JSON response
                    }
                }
            });
        }
    }

    private void login() {
        // Call API login

        String username = textInputEditTextUsername.getText().toString();
        String password = textInputEditTextPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toasty.error(this, "Username or password is empty", Toast.LENGTH_SHORT).show();
        } else {
            // Call API login
            // Tạo JSON body
            LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);
            loadingDialog.startLoadingDialog();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", username);
                jsonBody.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Tạo request body
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    jsonBody.toString());

            // Tạo yêu cầu POST
            Request request = new Request.Builder()
                    .url(baseURL + "/api/v1/auth/authenticate")
                    .post(requestBody)
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
                            loadingDialog.dismissDialog();
                            Toasty.error(getApplicationContext(), "Kết nối với máy chủ thất bại! ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();

                        editor.putString("name", jsonObject.getString("name"));
                        editor.putString("email", jsonObject.getString("email"));
                        editor.putString("phone", jsonObject.getString("phone"));
                        editor.putString("role", jsonObject.getString("role"));
                        editor.putString("address", jsonObject.getString("address"));
                        editor.putString("gender", jsonObject.getString("gender"));
                        editor.putString("imageUrl", jsonObject.getString("imageUrl"));

                        editor.apply();

//

                        String token = jsonObject.getString("token");
                        RoleUser roleUser = RoleUser.valueOf(jsonObject.getString("role"));
                        SharedPreferences.Editor editor2 = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                        editor2.putString("token", token);
                        editor2.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), roleUser.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (roleUser.equals(RoleUser.FAMILY_MEMBER)) {
                            Intent intent = new Intent(LoginActivity.this, MainMainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (roleUser.equals(RoleUser.DOCTOR) || roleUser.equals(RoleUser.NURSE)) {
                            Intent intent = new Intent(LoginActivity.this, DocterAndNurseActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (roleUser.equals(RoleUser.ADMIN)) {
                            Intent intent = new Intent(LoginActivity.this, MainMainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}