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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.ListUserActivity.ListUserActivity;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.subactivities.SettingUserActivity;
import com.example.nursinghome_android.valueStatic.ChooseFuture;

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
    TextView textViewUserName, textViewUserAddress;

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
        buttonSettingUser = view.findViewById(R.id.imageViewSetingUser);

        SharedPreferences prefs1 = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = prefs1.getString("name", "@drawable/chandung1");
        textViewUserName = view.findViewById(R.id.textViewUserName);
        textViewUserName.setText("Xin chào, " + name);

        String address = prefs1.getString("address", null);
        textViewUserAddress = view.findViewById(R.id.textViewUserAddress);
        textViewUserAddress.setText(address);

        String imageUrl = prefs1.getString("imageUrl", null);

        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().transform(new CenterCrop(), new CircleCrop()))
                .into(buttonSettingUser);


        CardView cardView = view.findViewById(R.id.cardViewServiceInfo);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "BookingService";
            startActivity(intent);
        });

        ImageView imageView = view.findViewById(R.id.imageServiceInfo);
        Glide.with(getContext())
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714754081/x5bpah2an8yickjix0kh.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView);

        CardView cardView1 = view.findViewById(R.id.cardViewDatlichTham);
        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "BookingLichTham";
            startActivity(intent);
        });

        ImageView imageView1 = view.findViewById(R.id.imageDatlichTham);
        Glide.with(getContext())
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714755039/enmgpxmhaxytyzrfqrud.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView1);

        CardView cardView2 = view.findViewById(R.id.cardViewTheoDoiSucKhoe);
        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ListUserActivity.class);
            ChooseFuture.chooseFuture = "health";
            startActivity(intent);
        });

        ImageView imageView2 = view.findViewById(R.id.imageTheoDoiSucKhoe);
        Glide.with(getContext())
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714756033/j9gqy3vhaygsesqn9xeq.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView2);

        return view;
    }
}