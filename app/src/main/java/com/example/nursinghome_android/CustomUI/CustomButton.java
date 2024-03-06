package com.example.nursinghome_android.CustomUI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.nursinghome_android.R;

public class CustomButton extends LinearLayout {

    private ImageView mIconImageView;
    private TextView mLabelTextView;

    public CustomButton(Context context) {
        super(context);
        init(context);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_button, this, true);
        mIconImageView = findViewById(R.id.imageViewIcon);
        mLabelTextView = findViewById(R.id.textViewLabel);
    }

    public void setIcon(int iconResId) {
        mIconImageView.setImageResource(iconResId);
    }

    public void setLabel(String labelText) {
        mLabelTextView.setText(labelText);
    }
}
