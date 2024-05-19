package com.example.nursinghome_android.ListViewSetUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DropdownAdapterLichSuDangKiDichVu extends ArrayAdapter<Object[]> {

    private LayoutInflater mInflater;
    private List<Object[]> mData;
    private OnClickListener mClickListener;

    public DropdownAdapterLichSuDangKiDichVu(Context context, List<Object[]> data, OnClickListener clickListener) {
        super(context, R.layout.list_item_dropdown_servicerecord, data);
        mInflater = LayoutInflater.from(context);
        mData = data;
        mClickListener = clickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_dropdown_servicerecord, parent, false);
            holder = new ViewHolder();
            holder.tvMaHoaDon = convertView.findViewById(R.id.tvMaHoaDon);
            holder.tvDichVuDangKi = convertView.findViewById(R.id.tvDichVuDangKi);
            holder.tvNguoiThuHuong = convertView.findViewById(R.id.tvNguoiThuHuong);
            holder.tvNgayDangKi = convertView.findViewById(R.id.tvNgayDangKi);
            holder.tvThoiGianBatDau = convertView.findViewById(R.id.tvThoiGianBatDau);
            holder.tvThoiGianKetThuc = convertView.findViewById(R.id.tvThoiGianKetThuc);
            holder.tvChiPhi = convertView.findViewById(R.id.tvChiPhi);
            holder.tvTrangThaiThanhToan = convertView.findViewById(R.id.tvTrangThaiThanhToan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu tại vị trí
        Object[] item = mData.get(position);

        // Thiết lập dữ liệu cho các thành phần giao diện
        holder.tvMaHoaDon.setText(StringToLong(item[0].toString()));
        holder.tvDichVuDangKi.setText(item[1].toString());
        holder.tvNguoiThuHuong.setText(item[2].toString());
        holder.tvNgayDangKi.setText(convertToReadableTimeFormat(item[3].toString()));
        holder.tvThoiGianBatDau.setText(convertToReadableTimeFormat(item[4].toString()));
        holder.tvThoiGianKetThuc.setText(convertToReadableTimeFormat(item[5].toString()));
        holder.tvChiPhi.setText(item[6].toString());
        holder.tvTrangThaiThanhToan.setText(convertPaidAndUnPaid(item[7].toString()));


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
        TextView tvMaHoaDon;
        TextView tvDichVuDangKi;
        TextView tvNguoiThuHuong;
        TextView tvNgayDangKi;
        TextView tvThoiGianBatDau;
        TextView tvThoiGianKetThuc;
        TextView tvChiPhi;
        TextView tvTrangThaiThanhToan;
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
    public String convertPaidAndUnPaid(String status){
        if(status.equals("PAID")){
            return "Đã thanh toán";
        }else{
            return "Chưa thanh toán";
        }
    }

    public String StringToLong(String string){
        Double doubleValue = Double.valueOf(string);
        Long longValue = doubleValue.longValue();
        return longValue.toString();
    }
}
