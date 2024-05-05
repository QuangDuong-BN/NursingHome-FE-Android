package com.example.nursinghome_android.FragmentMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.chatrealtime.MyZIMKitActivity;
import com.example.nursinghome_android.valueStatic.ChatID;
import com.zegocloud.zimkit.common.ZIMKitRouter;
import com.zegocloud.zimkit.common.enums.ZIMKitConversationType;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoBlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoBlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoBlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoBlankFragment newInstance(String param1, String param2) {
        InfoBlankFragment fragment = new InfoBlankFragment();
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
        View view = inflater.inflate(R.layout.fragment_info_blank, container, false);

        ImageView imageView = view.findViewById(R.id.doctorimage1);
        Glide.with(getContext())
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714842952/farkcap74koxamb1pjrd.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView);

        Button button = view.findViewById(R.id.NhanTin1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatID.chatID = "bacsi1";
                startActivity(new Intent(getActivity(), MyZIMKitActivity.class));
            }
        });

        ImageView imageView1 = view.findViewById(R.id.doctorimage2);
        Glide.with(getContext())
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714842868/otp8siickyzt5gcnppga.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView1);

        Button button1 = view.findViewById(R.id.NhanTin2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatID.chatID = "ddhuyeen";
                startActivity(new Intent(getActivity(), MyZIMKitActivity.class));
            }
        });


        return view;
    }
}