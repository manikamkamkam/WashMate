package com.example.washmate.view.customDialog;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.example.washmate.R;

public class paymentStatusDialog {
    Activity activity;
    AlertDialog dialog;
    DialogInterface.OnClickListener onClickListener;
    public paymentStatusDialog(Activity activity,DialogInterface.OnClickListener onClickListener)
    {
        this.activity = activity;
        this.onClickListener = onClickListener;
    }

    public void startSuccessDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.CustomDialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.payment_status_success,null)).setPositiveButton("OK",onClickListener);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();


    }
    public void startfailedDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.CustomDialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.payment_status_fail,null)).setPositiveButton("OK",onClickListener);
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
