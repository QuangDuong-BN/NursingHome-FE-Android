package com.example.nursinghome_android.FragmentDatLichTham;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nursinghome_android.ListViewSetUp.DropdownAdapterVisitRecord;
import com.example.nursinghome_android.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichSuBookingBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichSuBookingBlankFragment extends Fragment implements DropdownAdapterVisitRecord.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LichSuBookingBlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichSuBookingBlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichSuBookingBlankFragment newInstance(String param1, String param2) {
        LichSuBookingBlankFragment fragment = new LichSuBookingBlankFragment();
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
        View view = inflater.inflate(R.layout.fragment_lich_su_booking_blank, container, false);
        // Inflate the layout for this fragment

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/visitRecord/get-all-for-user")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về
                    String result = response.body().string();
                    List<Object[]> mDropdownItems = parseJsonToServiceInfoList(result);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mDropdownItems != null && !mDropdownItems.isEmpty()) {
                                ListView mDropdownListView = view.findViewById(R.id.listViewFragmentLichSuBookingBlank);
                                // Sử dụng mDropdownListView đã được khởi tạo
                                DropdownAdapterVisitRecord mAdapter = new DropdownAdapterVisitRecord(getActivity(), mDropdownItems, LichSuBookingBlankFragment.this);
                                mDropdownListView.setAdapter(mAdapter);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toasty.error(getActivity(), "Kết nối thất bại!", Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onItemClick(Long id) {
        Toasty.info(getActivity(), "Chức năng xem chi tiết sẽ được cập nhật sau!", Toasty.LENGTH_SHORT).show();

    }

    public static List<Object[]> parseJsonToServiceInfoList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }
}