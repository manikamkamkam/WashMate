package com.example.washmate.view.customDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.washmate.R;
import com.example.washmate.model.appointment;
import com.example.washmate.model.role.User;
import com.example.washmate.view.customer.Appointment;

public class appointmentDetailsDialog {

    Activity activity;
    AlertDialog dialog;
    View.OnClickListener acceptBtnListener;
    View.OnClickListener cancelBtnListener;
    public appointmentDetailsDialog(Activity activity,View.OnClickListener accBtn,View.OnClickListener canBtn)
    {
        this.activity = activity;
        this.acceptBtnListener = accBtn;
        this.cancelBtnListener = canBtn;
    }

    public void startDialog(User user, appointment appointment)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.requestdetails_dialog,null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
        Button acceptBtn = dialog.findViewById(R.id.acceptBtn_dialog);
        Button cancelBtn =  dialog.findViewById(R.id.cancelBtn_dialog);
        assert acceptBtn != null;
        acceptBtn.setOnClickListener(acceptBtnListener);
        assert cancelBtn != null;
        cancelBtn.setOnClickListener(cancelBtnListener);

        TextView custName_dialog = dialog.findViewById(R.id.custName_dialog);
        TextView custPhoNo = dialog.findViewById(R.id.custPhNo_dialog);
        TextView custCartypeNo= dialog.findViewById(R.id.carType_No_dialog);
        TextView location = dialog.findViewById(R.id.location_dialog);
        TextView date_time = dialog.findViewById(R.id.date_dialog_time);
        TextView price = dialog.findViewById(R.id.price_dialog);

        custName_dialog.setText(user.getFullName());
        custPhoNo.setText(user.getPhoneNumber());
        custCartypeNo.setText(appointment.getCarType()+ " "+appointment.getCarPlateNo());
        location .setText(appointment.getLocation());
        date_time.setText(appointment.getDate()+" \n "+appointment.getTime());
        price .setText("RM "+appointment.getPrice());
    }
    public void dismissDialog()
    {
        dialog.dismiss();
    }
}
