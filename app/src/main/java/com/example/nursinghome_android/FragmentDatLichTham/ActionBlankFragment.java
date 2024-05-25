package com.example.nursinghome_android.FragmentDatLichTham;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.nursinghome_android.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionBlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ActionBlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActionBlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionBlankFragment newInstance(String param1, String param2) {
        ActionBlankFragment fragment = new ActionBlankFragment();
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
// Đặt ngôn ngữ của ứng dụng thành tiếng Việt
        Locale locale = new Locale("vi");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        CalendarView datePicker;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_action_blank, container, false);


        datePicker = view.findViewById(R.id.datePickerActionFrament);
        // Lấy thời gian hiện tại từ datePicker
        long currentTimeMillis = datePicker.getDate();

        // Chuyển đổi thời gian thành đối tượng Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);

        // Định dạng ngày tháng năm thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());
        TextView tvdateOfAction = view.findViewById(R.id.tvdateOfAction);
        tvdateOfAction.setText("Hoạt động ngày: " + dateString);

        callApiSetTextActionFragment(dateString, view);

        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", (month + 1)) + "-" + dayOfMonth;
            CardView cardViewMorning = view.findViewById(R.id.cardViewMorning);
            CardView cardViewAfternoon = view.findViewById(R.id.cardViewAfternoon);
            cardViewMorning.setVisibility(CardView.INVISIBLE);
            cardViewAfternoon.setVisibility(CardView.INVISIBLE);

            new Handler().postDelayed(() -> {
                cardViewMorning.setVisibility(CardView.VISIBLE);
                cardViewAfternoon.setVisibility(CardView.VISIBLE);
            }, 100);

            tvdateOfAction.setText("Hoạt động ngày: " + date);
            callApiSetTextActionFragment(date, view);

        });


        return view;
    }

    public void callApiSetTextActionFragment(String dateOfAction, View view) {
        // ket noi voi server, lay thong tin cua nguoi than


        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        // Tạo yêu cầu GET
        Request request = new Request.Builder()
                .url(baseURL + "/action/get?dateOfAction=" + dateOfAction)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        // Tạo OkHttpClient
        OkHttpClient client = new OkHttpClient();
        // Thực hiện yêu cầu bất đồng bộ
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    List<Object[]> object = parseJsonToServiceInfoList(myResponse);

                    getActivity().runOnUiThread(() -> {
                        // do something...
                        TextView tvActionMorning = view.findViewById(R.id.tvActionMorning);
                        TextView tvIsVistAbleMorning = view.findViewById(R.id.tvIsVistAbleMorning);
                        TextView tvActionAfternoon = view.findViewById(R.id.tvActionAfternoon);
                        TextView tvIsVistAbleAfternoon = view.findViewById(R.id.tvIsVistAbleAfternoon);
                        if (object.size() == 0) {
                            tvActionMorning.setText("Không có hoạt động nào");
                            tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                            tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));

                            tvActionAfternoon.setText("Không có hoạt động nào");
                            tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                            tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));

                        }

                        if (object.size() == 1) {
                            if (object.get(0)[3].equals("MORNING")) {
                                tvActionMorning.setText(object.get(0)[0].toString() + ":\n" + object.get(0)[1].toString());
                                if (object.get(0)[4].toString().equals("false")) {
                                    tvIsVistAbleMorning.setText("Lưu ý: Không thể đặt lịch thăm");
                                    tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.red));
                                } else {
                                    tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                                    tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));
                                }

                                tvActionAfternoon.setText("Không có hoạt động nào");
                                tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));


                            } else {
                                tvActionAfternoon.setText(object.get(0)[0].toString() + ":\n" + object.get(0)[1].toString());
                                if (object.get(0)[4].toString().equals("false")) {
                                    tvIsVistAbleAfternoon.setText("Lưu ý: Không thể đặt lịch thăm");
                                    tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.red));
                                } else {
                                    tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                                    tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));
                                }

                                tvActionMorning.setText("Không có hoạt động nào");
                                tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));
                            }
                        }
                        if (object.size() == 2) {
                            tvActionMorning.setText(object.get(0)[0].toString() + ":\n" + object.get(0)[1].toString());
                            if (object.get(0)[4].toString().equals("false")) {
                                tvIsVistAbleMorning.setText("Lưu ý: Không thể đặt lịch thăm");
                                tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.red));
                            } else {
                                tvIsVistAbleMorning.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleMorning.setTextColor(getResources().getColor(R.color.green));
                            }
                            tvActionAfternoon.setText(object.get(1)[0].toString() + ":\n" + object.get(1)[1].toString());
                            if (object.get(1)[4].toString().equals("false")) {
                                tvIsVistAbleAfternoon.setText("Lưu ý: Không thể đặt lich thăm");
                                tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.red));
                            } else {
                                tvIsVistAbleAfternoon.setText("Lưu ý: Có thể đặt lịch thăm");
                                tvIsVistAbleAfternoon.setTextColor(getResources().getColor(R.color.green));
                            }
                        }

                    });
                }
            }
        });
    }

    public static List<Object[]> parseJsonToServiceInfoList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Object[]>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }
}