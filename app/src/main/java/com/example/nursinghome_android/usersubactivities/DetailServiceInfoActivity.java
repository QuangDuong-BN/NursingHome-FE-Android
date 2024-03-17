package com.example.nursinghome_android.usersubactivities;

import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.BookingInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailServiceInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    String result;
    Button buttonRegisterService;

    TextView textViewName, textViewAmenities, textViewNutritionMode, textViewCommunityActivities, textViewCareRegimen;
    ImageView imageViewPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service_info);

        toolbar = findViewById(R.id.toolbarDetailServiceInfo);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        buttonRegisterService = findViewById(R.id.buttonRegisterService);
        buttonRegisterService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailServiceInfoActivity.this, RegisterServiceActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/service_info/get_by_id?id=" + BookingInfo.serviceInfoIdFk)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            Long id = jsonResponse.getLong("id");
                            String name = jsonResponse.getString("name");
                            String amenities = jsonResponse.getString("amenities");
                            String nutritionMode = jsonResponse.getString("nutritionMode");
                            String communityActivities = jsonResponse.getString("communityActivities");
                            String careRegimen = jsonResponse.getString("careRegimen");
                            String stringimageurl = jsonResponse.getString("imageUrlPrice");

                            textViewName = findViewById(R.id.textViewNameService);
                            textViewAmenities = findViewById(R.id.textViewAmenities);
                            textViewNutritionMode = findViewById(R.id.textViewNutritionMode);
                            textViewCommunityActivities = findViewById(R.id.textViewCommunityActivities);
                            textViewCareRegimen = findViewById(R.id.textViewCareRegimen);
                            imageViewPrice = findViewById(R.id.imageViewPrice);

                            textViewName.setText(name);
                            textViewAmenities.setText(amenities);
                            textViewNutritionMode.setText(nutritionMode);
                            textViewCommunityActivities.setText(communityActivities);
                            textViewCareRegimen.setText(careRegimen);

                            Glide.with(DetailServiceInfoActivity.this)
                                    .load(stringimageurl)
                                    .into(imageViewPrice);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(DetailServiceInfoActivity.this, "Thất bại xin hay thực hiện lại", Toasty.LENGTH_SHORT).show();
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