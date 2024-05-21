package com.example.nursinghome_android.FragmentDatLichTham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter1 extends FragmentStatePagerAdapter {


    public ViewPagerAdapter1(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActionBlankFragment();
            case 1:
                return new BookingBlankFragment();
            case 2:
                return new LichSuBookingBlankFragment();
            default:
                return new ActionBlankFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Hoạt động";
                break;
            case 1:
                title = "Đặt lịch";
                break;
            case 2:
                title = "Lịch sử";
                break;
        }
        return title;
    }
}

