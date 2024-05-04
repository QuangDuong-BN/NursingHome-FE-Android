package com.example.nursinghome_android.config;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.nursinghome_android.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;

    public LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }
    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.custom_loading_dialog, null));
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog() {
        alertDialog.dismiss();
    }
}
