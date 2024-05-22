package com.example.nursinghome_android.FragmentDatLichTham;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.nursinghome_android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

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
        String date;
        Spinner timeSpinner = view.findViewById(R.id.timeSpinner);
        String timeOfDay = timeSpinner.getSelectedItem().toString();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thời gian hiện tại từ datePicker
                long currentTimeMillis = datePicker.getDate();

                // Chuyển đổi thời gian thành đối tượng Calendar
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(currentTimeMillis);

                // Định dạng ngày tháng năm thành chuỗi
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateString = dateFormat.format(calendar.getTime());

                String timeOfDay = timeSpinner.getSelectedItem().toString();

                Toasty.success(getContext(), timeOfDay + "---" + dateString, Toasty.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}