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
import com.example.washmate.model.appointment;
import com.example.washmate.model.role.User;
import com.example.washmate.model.role.contractor;
import com.example.washmate.view.customDialog.LoadingDialog;
import com.example.washmate.view.customDialog.appointmentDetailsDialog;

import java.util.ArrayList;

public class CarwashRequest extends AppCompatActivity  {

    ArrayList<appointment> requests;
    ArrayList<User> userInfo = new ArrayList<User>();
    ListView requestlistView;
    LoadingDialog ld;
    contractor cont = contractor.getLoggedinUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractor_carwashrequest_activity);
        requestlistView = findViewById(R.id.requestList);
        ld = new LoadingDialog(this);
        ld.startLoadingDialog();
    }

    public void onResume() {
        super.onResume();
        readyToUpdate();
    }

    private void readyToUpdate() {
        requests = new appointment().getAllAppoinmentWithStatusIncoming(new appointment.TaskCompletedCallBack() {

            @Override
            public void isTaskCompleted(boolean isCompleted) {
            getUserInfoforApppoinemnt();
            }
        });
    }


    private void getUserInfoforApppoinemnt()
    {  User temp;
        for(appointment request : requests) {
        new User().getUserByUserId(request.getCustomerId(), new User.getUserCallBack() {
              @Override
              public void getUserCallBack(User user) {
                  userInfo.add(user);
              }
          },new User.TaskOnCompletedCallBack(){
            @Override
            public void isTaskCompleted(boolean available) {
              updateView();  ld.dismissDialog();
            }
        });
        }


    }

    private void updateView() {
       appointmentlistadapter adapter = new appointmentlistadapter(this, R.layout.carwashrequestlist_row, requests,userInfo);
        requestlistView.setAdapter(adapter);

    }



    class appointmentlistadapter extends ArrayAdapter<appointment> {
        Context mcontext;
        int mresource;

        public appointmentlistadapter(@NonNull Context context, int resource, @NonNull ArrayList<appointment> appointments,@NonNull ArrayList<User> userInfo) {
            super(context, resource, appointments);
            mcontext = context;
            mresource = resource;
        }
            @NonNull
            @Override
            public View getView (int position, @Nullable View convertView,@NonNull ViewGroup parent){
                LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
                convertView = layoutInflater.inflate(mresource, parent, false);
                TextView custName = convertView.findViewById(R.id.custName);
                TextView carType_No = convertView.findViewById(R.id.carType_carPLateNo);
                TextView custPhoneNo = convertView.findViewById(R.id.custPhoneNo);
                Button acceptBtn = convertView.findViewById(R.id.acceptBtn);
                custName.setText(userInfo.get(position).getFullName());
                carType_No.setText(getItem(position).getCarType()+" "+getItem(position).getCarPlateNo());
                custPhoneNo.setText(userInfo.get(position).getPhoneNumber());
                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   showAppointmentDetailsDialog(getItem(position),userInfo.get(position));
                    }
                });



                return convertView;
            }
        appointmentDetailsDialog detailsDialog;
        public void showAppointmentDetailsDialog(appointment selectedAppoint,User selectedUser)
            {
                detailsDialog = new appointmentDetailsDialog(CarwashRequest.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    selectedAppoint.setInchargeContractor(cont.getFullName(), new appointment.TaskCompletedCallBack() {
                        @Override
                        public void isTaskCompleted(boolean isCompleted) {
                        readyToUpdate();
                        detailsDialog.dismissDialog();

                        }
                    });
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    detailsDialog.dismissDialog();
                    }
                });

                detailsDialog.startDialog(selectedUser,selectedAppoint);

            }





    }





}