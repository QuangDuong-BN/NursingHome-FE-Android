package com.example.nursinghome_android.FragmentDatLichTham;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nursinghome_android.valueStatic.BaseURL.baseURL;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nursinghome_android.R;
import com.example.nursinghome_android.valueStatic.BookingInfo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingBlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingBlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingBlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingBlankFragment newInstance(String param1, String param2) {
        BookingBlankFragment fragment = new BookingBlankFragment();
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
        View view = inflater.inflate(R.layout.fragment_booking_blank, container, false);
        Button registerButton = view.findViewById(R.id.registerButton);
        CalendarView datePicker = view.findViewById(R.id.datePickerBookingFrament);
        Spinner timeSpinner = view.findViewById(R.id.timeSpinner);
        TextInputEditText edtDate = view.findViewById(R.id.edtDate);

        // Lấy thời gian hiện tại từ datePicker
        long currentTimeMillis = datePicker.getDate();
        // Chuyển đổi thời gian thành đối tượng Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        // Định dạng ngày tháng năm thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());
        edtDate.setText(dateString);

        datePicker.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", (dayOfMonth ));
            edtDate.setText(date);
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeOfDay = timeSpinner.getSelectedItem().toString();
                if (timeOfDay.equals("Buổi sáng (8h30 - 11h)")) {
                    timeOfDay = "MORNING";
                } else {
                    timeOfDay = "AFTERNOON";
                }


                SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String token = prefs.getString("token", null);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("visitedId", BookingInfo.userIdFk);
                    jsonBody.put("timeOfDay", timeOfDay);
                    jsonBody.put("visitDate", edtDate.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toasty.info(getActivity(), jsonBody.toString(), Toasty.LENGTH_SHORT).show();

                // Tạo request body
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        jsonBody.toString());

                // Tạo yêu cầu GET
                Request request = new Request.Builder()
                        .url(baseURL + "/visitRecord/add")
                        .addHeader("Authorization", "Bearer " + token)
                        .post(requestBody)
                        .build();

                // Tạo OkHttpClient
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body().string();

                        if (response.isSuccessful()) {
                            // Xử lý dữ liệu trả về
                            int statusCode = response.code(); // Lấy mã HTTP
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#0091c1"));
                                    pDialog.setTitleText("Đăng kí thành công!");
                                    pDialog.setCancelable(true);
                                    pDialog.show();
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SweetAlertDialog pDialog1 = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                                    pDialog1.getProgressHelper().setBarColor(Color.parseColor("#0091c1"));
                                    pDialog1.setTitleText(result);
                                    pDialog1.setCancelable(true);
                                    pDialog1.show();
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

            }
        });

        return view;
    }
}