package com.example.nursinghome_android.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.example.nursinghome_android.FragmentDocter.DocterHomeFragment;
import com.example.nursinghome_android.FragmentUser.HomeBlankFragment;
import com.example.nursinghome_android.FragmentUser.InfoBlankFragment;
import com.example.nursinghome_android.FragmentUser.SettingBlankFragment;
import com.example.nursinghome_android.R;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class DocterAndNurseActivity extends AppCompatActivity {
    private SmoothBottomBar smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_and_nurse);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        rePlace(new DocterHomeFragment());
        smoothBottomBar = findViewById(R.id.bottomBar_Docter);
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        rePlace(new DocterHomeFragment());
                        break;
                    case 1:
                        rePlace(new SettingBlankFragment());
                        break;
                }
                return false;
            }
        });
    }
    private void rePlace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Fragment_main_Docter, fragment);
        transaction.commit();
    }
}