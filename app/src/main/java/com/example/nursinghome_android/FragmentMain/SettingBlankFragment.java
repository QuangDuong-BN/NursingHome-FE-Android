package com.example.nursinghome_android.FragmentMain;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.ListUserActivity.ListUserActivity;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.mainactivity.LoginActivity;
import com.example.nursinghome_android.valueStatic.ChooseFuture;
import com.example.nursinghome_android.valueStatic.UserInfoStatic;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingBlankFragment extends Fragment {
    Button buttonLogin;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingBlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingBlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingBlankFragment newInstance(String param1, String param2) {
        SettingBlankFragment fragment = new SettingBlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_blank, container, false);
        buttonLogin = (Button) view.findViewById(R.id.btnLogout);
        buttonLogin.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
            editor.putString("token", null);
            editor.apply();

            UserInfoStatic.id = null;
            UserInfoStatic.name = null;
            UserInfoStatic.email = null;
            UserInfoStatic.phone = null;
            UserInfoStatic.role = null;

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });


        ImageView imageView = view.findViewById(R.id.imgUser);
        SharedPreferences prefs1 = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUrl = prefs1.getString("imageUrl", null);
        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().transform(new CenterCrop(), new CircleCrop()))
                .into(imageView);

        Button button = view.findViewById(R.id.btnLichSuDangKiDichVu);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), com.example.nursinghome_android.usersubactivities.LichSuDangKiDichVu.class);
            startActivity(intent);
        });

        Button btnChinhSuaThongTinTaiKhoan = view.findViewById(R.id.btnChinhSuaThongTinTaiKhoan);
        btnChinhSuaThongTinTaiKhoan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), com.example.nursinghome_android.usersubactivities.ChinhSuaThongTinTaiKhoanActivity.class);
            startActivity(intent);
        });

        Button button1 = view.findViewById(R.id.btnQuanLyThongTinNguoiThan);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "CaiDatThongTinNguoiThan";
            startActivity(intent);
            Toasty.info(getActivity(), "Chức năng đang phát triển", Toasty.LENGTH_SHORT).show();
        });

        return view;

    }
}