package com.example.nursinghome_android;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nursinghome_android.FragmentUser.HomeBlankFragment;
import com.example.nursinghome_android.FragmentUser.InfoBlankFragment;
import com.example.nursinghome_android.FragmentUser.SettingBlankFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainMainActivity extends AppCompatActivity {
    private SmoothBottomBar smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(getResources().getColor(R.color.white));
//        }
        rePlace(new HomeBlankFragment());
        smoothBottomBar = findViewById(R.id.bottomBar);
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        rePlace(new HomeBlankFragment());
                        break;
                    case 1:
                        rePlace(new InfoBlankFragment());
                        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                        break;

                    case 2:
                        rePlace(new SettingBlankFragment());
                        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                        break;
                }
                return false;
            }
        });
    }

    private void rePlace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Fragment_main, fragment);
        transaction.commit();
    }


}