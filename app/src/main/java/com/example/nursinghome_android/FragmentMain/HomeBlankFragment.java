package com.example.nursinghome_android.FragmentMain;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nursinghome_android.ListUserActivity.ListUserActivity;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.mainactivity.UserActivity;
import com.example.nursinghome_android.subactivities.SettingUserActivity;
import com.example.nursinghome_android.valueStatic.BaseURL;
import com.example.nursinghome_android.valueStatic.ChooseFuture;
import com.example.nursinghome_android.valueStatic.UserInfoStatic;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeBlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonServiceInfo, buttonDatlichTham, buttonThongTinSucKhoe, buttonMealPlan;
    ImageButton buttonSettingUser;
    TextView textViewUserName,textViewUserAddress;

    public HomeBlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeBlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeBlankFragment newInstance(String param1, String param2) {
        HomeBlankFragment fragment = new HomeBlankFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_blank, container, false);


        SharedPreferences prefs1 = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = prefs1.getString("name", null);
        textViewUserName = view.findViewById(R.id.textViewUserName);
        textViewUserName.setText("Xin chào, " + name);

        String address = prefs1.getString("address", null);
        textViewUserAddress = view.findViewById(R.id.textViewUserAddress);
        textViewUserAddress.setText(address);

        buttonServiceInfo = view.findViewById(R.id.buttonServiceInfo);
        buttonServiceInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "BookingService";
            startActivity(intent);
        });

        buttonDatlichTham = view.findViewById(R.id.buttonDatlichTham);
        buttonDatlichTham.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "BookingLichTham";
            startActivity(intent);
        });

        buttonSettingUser = view.findViewById(R.id.imageViewSetingUser);
        buttonSettingUser.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingUserActivity.class);
            startActivity(intent);
        });

        buttonMealPlan = view.findViewById(R.id.buttonMealPlan);
        buttonMealPlan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "MealPlan";
            startActivity(intent);
        });

        buttonThongTinSucKhoe = view.findViewById(R.id.buttonThongTinSucKhoe);
        buttonThongTinSucKhoe.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "health";
            startActivity(intent);
        });

        return view;
    }
}