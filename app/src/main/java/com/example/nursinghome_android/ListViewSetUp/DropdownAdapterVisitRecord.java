package com.example.nursinghome_android.ListViewSetUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nursinghome_android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DropdownAdapterVisitRecord extends ArrayAdapter<Object[]> {

    private LayoutInflater mInflater;
    private List<Object[]> mData;
    private OnClickListener mClickListener;

    public DropdownAdapterVisitRecord(Context context, List<Object[]> data, OnClickListener clickListener) {
        super(context, R.layout.list_item_dropdown_visitrecord, data);
        mInflater = LayoutInflater.from(context);
        mData = data;
        mClickListener = clickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_dropdown_visitrecord, parent, false);
            holder = new ViewHolder();
            holder.tvIdVisitRecord = convertView.findViewById(R.id.tvIdVisitRecord);
            holder.tvTenTaiKhoan = convertView.findViewById(R.id.tvTenTaiKhoan);
            holder.tvTenNguoiThan = convertView.findViewById(R.id.tvTenNguoiThan);
            holder.tvKhungGio = convertView.findViewById(R.id.tvKhungGio);
            holder.tvNgayTham = convertView.findViewById(R.id.tvNgayTham);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu tại vị trí
        Object[] item = mData.get(position);

        // Thiết lập dữ liệu cho các thành phần giao diện
        holder.tvIdVisitRecord.setText(StringToLong(item[0].toString()));
        holder.tvTenTaiKhoan.setText(item[1].toString());
        holder.tvTenNguoiThan.setText(item[2].toString());
        holder.tvKhungGio.setText(convertMoringAndAfternoon(item[3].toString()));
        holder.tvNgayTham.setText(item[4].toString());


        // Gắn OnClickListener cho convertView
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    // Chuyển đổi chuỗi thành Double trước
                    Double doubleValue = Double.valueOf(item[0].toString());
                    // Chuyển đổi Double thành Long
                    Long longValue = doubleValue.longValue();
                    mClickListener.onItemClick(longValue);
                }
            }
        });

        return convertView;
    }

    // Interface để xử lý sự kiện khi mục được nhấn
    public interface OnClickListener {
        void onItemClick(Long id);
    }

    // ViewHolder pattern để tối ưu hóa hiệu suất
    static class ViewHolder {
        TextView tvIdVisitRecord;
        TextView tvTenTaiKhoan;
        TextView tvTenNguoiThan;
        TextView tvKhungGio;
        TextView tvNgayTham;
    }

    public String convertToReadableTimeFormat(String originalTime) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        SimpleDateFormat readableFormat = new SimpleDateFormat("hh:mm:ss a dd-MM-yyyy", Locale.US);
        try {
            Date date = originalFormat.parse(originalTime);
            return readableFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String convertMoringAndAfternoon(String status){
        if(status.equals("MORNING")){
            return "Buổi sáng (8h30 - 11h)";
        }else{
            return "Buổi chiều (14h - 16h30)";
        }
    }

    public String StringToLong(String string){
        Double doubleValue = Double.valueOf(string);
        Long longValue = doubleValue.longValue();
        return longValue.toString();
    }
}
