package com.example.washmate.view.contractor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.washmate.R;
import com.example.washmate.model.WithDrawRequest;
import com.example.washmate.model.appointment;
import com.example.washmate.model.role.contractor;
import com.example.washmate.view.customDialog.LoadingDialog;
import com.example.washmate.view.customDialog.withdrawalDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class withDraw  extends AppCompatActivity {
    ListView requestList;
    LoadingDialog ld;
    contractor cont = contractor.getLoggedinUser();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractor_balance_activity);
        withdrawalDialog wDialog = new withdrawalDialog(this);
        Button withDrawBtn = findViewById(R.id.withDrawBtn);
        ld = new LoadingDialog(this);
        ld.startLoadingDialog();
        requestList= findViewById(R.id.withdrawHistory);
        withDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wDialog.startDialog(withDraw.this);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        readyToUpdate();
    }
 ArrayList<WithDrawRequest> request;
    public void readyToUpdate() {
       request = new WithDrawRequest().getAllRequest(new WithDrawRequest.onTaskCompletedCallBack() {
           @Override
           public void isTaskCompleted(boolean taskCompleted) {
            updateView();
           }
       });
    }

    public void updateView() {
        TextView bal = findViewById(R.id.bal);
        bal.setText(String.format("%.2f",cont.getBalance()));
        requestlistadapter adapter = new requestlistadapter (this, R.layout.withdrawrequest_row, request);
        requestList.setAdapter(adapter);
        ld.dismissDialog();
    }

    class requestlistadapter extends ArrayAdapter<WithDrawRequest> {
        Context mcontext;
        int mresource;

        public requestlistadapter(@NonNull Context context, int resource, @NonNull ArrayList<WithDrawRequest> objects) {
            super(context, resource, objects);
            mcontext = context;
            mresource = resource;

        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(mresource, parent, false);
            TextView amount = convertView.findViewById(R.id.amount);
            TextView status = convertView.findViewById(R.id.status);
            amount.setText(String.format("RM %.2f",getItem(position).getWithDrawAmount()));

            status.setText(getItem(position).getStatus());

           return convertView;
        }


    }
}
