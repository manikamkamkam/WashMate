package com.example.washmate.view.customDialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.washmate.R;
import com.example.washmate.model.WithDrawRequest;
import com.example.washmate.model.appointment;
import com.example.washmate.model.role.User;
import com.example.washmate.model.role.contractor;
import com.example.washmate.view.contractor.withDraw;

public class withdrawalDialog {
        Activity activity;
        AlertDialog dialog;
        EditText amount;
        EditText desc ;
        LoadingDialog ld;
        contractor cont = contractor.getLoggedinUser();
       withDraw wd;


    public withdrawalDialog(Activity activity)
        {
            this.activity = activity;
            ld = new LoadingDialog(activity);

        }

        public void startDialog(withDraw wd)
        {
            this .wd= wd;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.withdraw_dialog,null));
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();
            Button submitBtn = dialog.findViewById(R.id.submitBtn);
            ImageButton closeBtn = dialog.findViewById(R.id.closeBtn);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog();
                }
            });

          amount = dialog.findViewById(R.id.amount);
          desc = dialog.findViewById(R.id.desc);


            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ld.startLoadingDialog();
                    saveRequest();

                }
            });



        }
        public void saveRequest()
        {
            if(cont.getBalance()<Double.parseDouble(amount.getText().toString()))
            {
                amount.setError("Balance Insufficient!");
                ld.dismissDialog();
            }
            else {
                WithDrawRequest WR = new WithDrawRequest(Double.parseDouble(amount.getText().toString()), desc.getText().toString(), cont.getUId());
                WR.SavetoFirebase(new WithDrawRequest.onTaskCompletedCallBack() {
                    @Override
                    public void isTaskCompleted(boolean taskCompleted) {
                        ld.dismissDialog();
                        dismissDialog();
                        wd.readyToUpdate();
                    }
                });
            }
        }
        public void dismissDialog()
        {
            dialog.dismiss();
        }
    }

