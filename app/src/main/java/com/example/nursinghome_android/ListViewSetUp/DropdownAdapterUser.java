package com.example.nursinghome_android.ListViewSetUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.entityDTO.UserItemforListUserDTO;

import java.util.List;

public class DropdownAdapterUser extends ArrayAdapter<UserItemforListUserDTO> {

    private LayoutInflater mInflater;
    private List<UserItemforListUserDTO> mData;
    private OnClickListener mClickListener;

    public DropdownAdapterUser(Context context, List<UserItemforListUserDTO> data, OnClickListener clickListener) {
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
        UserItemforListUserDTO item = mData.get(position);

        // Thiết lập dữ liệu cho các thành phần giao diện
        holder.textViewServiceName.setText(item.getName());
        holder.textViewDescription.setText(item.getAddress());

        Glide.with(getContext())
                .load(item.getImageUrl())
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(holder.imageViewIcon);

        // Gắn OnClickListener cho convertView
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(item.getId());
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
        ImageView imageViewIcon;
        TextView textViewServiceName;
        TextView textViewDescription;
    }
}
