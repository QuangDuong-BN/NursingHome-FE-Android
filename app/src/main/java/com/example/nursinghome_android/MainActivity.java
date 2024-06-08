package com.example.nursinghome_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nursinghome_android.config.LoadingDialog;
import com.example.nursinghome_android.enumcustom.RoleUser;
import com.example.nursinghome_android.mainactivity.DocterAndNurseActivity;
import com.example.nursinghome_android.mainactivity.LoginActivity;
import com.example.nursinghome_android.valueStatic.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    String baseURL, token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) > 0) {

                    mSLideViewPager.setCurrentItem(getitem(-1), true);

                }

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) < 3)
                    mSLideViewPager.setCurrentItem(getitem(1), true);
                else {
                    SharedPreferences prefs1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();

                    baseURL = BaseURL.baseURL;
                    //baseURL = "http://192.168.43.167:8080";
                    editor.putString("baseURL", baseURL);
                    editor.apply();
                    token = prefs1.getString("token", null);
                    // check thong tin dang nhap
                    checkTokenAndRoleUser();
                    LoadingDialog loadingDialog1 = new LoadingDialog(MainActivity.this);
                    loadingDialog1.startLoadingDialog();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog1.dismissDialog();
                        }
                    }, 2000);
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                }

            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();

                baseURL = BaseURL.baseURL;
                //baseURL = "http://192.168.43.167:8080";
                editor.putString("baseURL", baseURL);
                editor.apply();
                token = prefs1.getString("token", null);
                // check thong tin dang nhap
                checkTokenAndRoleUser();

                LoadingDialog loadingDialog2 = new LoadingDialog(MainActivity.this);
                loadingDialog2.startLoadingDialog();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog2.dismissDialog();
                    }
                }, 2000);

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                finish();

            }
        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpindicator(int position) {

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorPrimary, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.colorPrimary, getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0) {

                backbtn.setVisibility(View.VISIBLE);

            } else {

                backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i) {

        return mSLideViewPager.getCurrentItem() + i;
    }

    private void checkTokenAndRoleUser() {

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
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        RoleUser roleUser = RoleUser.valueOf(jsonResponse.getString("role"));
                        if (roleUser.equals(RoleUser.FAMILY_MEMBER)) {
                            Intent intent = new Intent(MainActivity.this, MainMainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (roleUser.equals(RoleUser.DOCTOR) || roleUser.equals(RoleUser.NURSE)) {
                            Intent intent = new Intent(MainActivity.this, DocterAndNurseActivity.class);
                            startActivity(intent);
                            finish();
                        }
                         else if (roleUser.equals(RoleUser.ADMIN)) {
                            Intent intent = new Intent(MainActivity.this, MainMainActivity.class);
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


}