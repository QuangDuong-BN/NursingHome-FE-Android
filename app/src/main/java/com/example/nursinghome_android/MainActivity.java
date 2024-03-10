package com.example.nursinghome_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //http://192.168.3.101/service_info/get_all
    private static final String API_URL = "http://192.168.3.101:8080/service_info/get_all";
    private String JWT_TOKEN; // Thay thế bằng JWT token của bạn


    Button buttonCallAPI;
    TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonCallAPI = (Button) findViewById(R.id.buttonCallApi);
        textViewResponse = (TextView) findViewById(R.id.textViewResponse);

        buttonCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });

    }
    private void callAPI() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + JWT_TOKEN)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String phone = jsonObject.getString("phone");
                        String email = jsonObject.getString("email");
                        String website = jsonObject.getString("website");
                        String description = jsonObject.getString("description");
                        String image = jsonObject.getString("image");
                        Log.d("TAG", "onResponse: " + name + " " + address + " " + phone + " " + email + " " + website + " " + description + " " + image);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, responseData, Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    JSONObject jsonResponse = new JSONObject(responseData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Xử lý khi có lỗi xảy ra trong việc xử lý JSON response
                }
            }
        });

    }
}