package com.example.nursinghome_android.ListViewSetUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nursinghome_android.R;

import java.util.List;

public class DropdownAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private List<String> mData;
    private OnClickListener mClickListener;

    public DropdownAdapter(Context context, List<String> data, OnClickListener clickListener) {
        super(context, R.layout.list_item_dropdown, data);
        mInflater = LayoutInflater.from(context);
        mData = data;
        mClickListener = clickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_dropdown, parent, false);
            holder = new ViewHolder();
            holder.imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
            holder.textViewServiceName = convertView.findViewById(R.id.tvServiceName);
            holder.textViewDescription = convertView.findViewById(R.id.tvDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu tại vị trí
        String item = mData.get(position);

        // Thiết lập dữ liệu cho các thành phần giao diện
        holder.textViewServiceName.setText(item);
        holder.textViewDescription.setText("Description for " + item);

        // Gắn OnClickListener cho convertView
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(item);
                }
            }
        });

        return convertView;
    }

    // Interface để xử lý sự kiện khi mục được nhấn
    public interface OnClickListener {
        void onItemClick(String item);
    }

    // ViewHolder pattern để tối ưu hóa hiệu suất
    static class ViewHolder {
        ImageView imageViewIcon;
        TextView textViewServiceName;
        TextView textViewDescription;
    }
}
