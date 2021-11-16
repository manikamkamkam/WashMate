package com.example.washmate.view.loadingDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.washmate.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;

    public LoadingDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void startLoadingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }

    public void dismissDialog()
    {
        dialog.dismiss();
    }
}
