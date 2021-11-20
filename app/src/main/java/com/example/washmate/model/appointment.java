package com.example.washmate.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.washmate.model.role.contractor;
import com.example.washmate.model.role.customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class appointment {
    String UUId;
    String date;
    String time;
    String carType;
    String carPlateNo;
    String location;
    String price;
    String customerId;
    String inchargeContratorName;
    String Status;

    public appointment(String date, String time, String carType, String carPlateNo, String location, String price, String customerId) {

        this.date = date;
        this.time = time;
        this.carType = carType;
        this.carPlateNo = carPlateNo;
        this.location = location;
        this.price = price;
        this.customerId = customerId;
        Status = "Incoming";
    }

    public appointment(String UUId, String date, String time, String carType, String carPlateNo, String location, String price, String customerId, String inchargeContratorName, String status) {
        this.UUId = UUId;
        this.date = date;
        this.time = time;
        this.carType = carType;
        this.carPlateNo = carPlateNo;
        this.location = location;
        this.price = price;
        this.customerId = customerId;
        this.inchargeContratorName = inchargeContratorName;
        Status = status;
    }
public appointment()
{

}

    public String getUUId() {
        return UUId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setUUId(String UUId) {
        this.UUId = UUId;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInchargeContratorName() {
        return inchargeContratorName;
    }

    public void setInchargeContratorName(String inchargeContratorName) {
        this.inchargeContratorName = inchargeContratorName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void saveAppointmentToFirebase()
    {
        CollectionReference firebaseAppoinment = FirebaseFirestore.getInstance().collection("Appointments");

        firebaseAppoinment.add(this).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String UUId = documentReference.getId();
                setUUId(UUId);

            }
        });
    }


    public ArrayList<appointment> getCurrentUsersOrderWithStatusIncoming(TaskCompletedCallBack callBack) {
        ArrayList<appointment> appointments = new ArrayList<appointment>();
        FirebaseFirestore.getInstance().collection("Appointments").whereEqualTo("inchargeContratorName", contractor.getLoggedinUser().getFullName()).whereEqualTo("status","Incoming")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               Log.d("appoinments", "getting appoinemtns details");
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       appointments.add(new appointment(document.getId(), document.getString("date"), document.getString("time"), document.getString("carType"), document.getString("carPlateNo"), document.getString("location"), document.getString("price"), document.getString("customerId"), document.getString("inchargeContratorName"), document.getString("status")));
                                                       Log.d("appoinments", "date"+document.getData());
                                                   }
                                                   callBack.isTaskCompleted(true);

                                               }
                                           }

                                       }
                );
        return appointments;
    }

    public ArrayList<appointment> getCurrentUserOrdersWithStatusCompleted(TaskCompletedCallBack callBack) {
        ArrayList<appointment> appointments = new ArrayList<appointment>();
        FirebaseFirestore.getInstance().collection("Appointments").whereEqualTo("inchargeContratorName", contractor.getLoggedinUser().getFullName()).whereEqualTo("status","Completed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               Log.d("appoinments", "getting appoinemtns details");
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       appointments.add(new appointment(document.getId(), document.getString("date"), document.getString("time"), document.getString("carType"), document.getString("carPlateNo"), document.getString("location"), document.getString("price"), document.getString("customerId"), document.getString("inchargeContratorName"), document.getString("status")));
                                                       Log.d("appoinments", "date"+document.getData());
                                                   }
                                                   callBack.isTaskCompleted(true);

                                               }
                                           }

                                       }
                );
        return appointments;
    }

    public interface TaskCompletedCallBack
    {
        public void isTaskCompleted(boolean isCompleted);
    }

    public ArrayList<appointment> getAllAppoinmentWithStatusIncoming(TaskCompletedCallBack callBack)
    {
        ArrayList<appointment> appointments = new ArrayList<appointment>();
        FirebaseFirestore.getInstance().collection("Appointments").whereEqualTo("status","Incoming").whereEqualTo("inchargeContratorName",null)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("appoinments", "getting appoinemtns details");
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                appointments.add(new appointment(document.getId(), document.getString("date"), document.getString("time"), document.getString("carType"), document.getString("carPlateNo"), document.getString("location"), document.getString("price"), document.getString("customerId"), document.getString("inchargeContratorName"), document.getString("status")));
                                Log.d("appoinments", "date"+document.getData());
                            }
                            callBack.isTaskCompleted(true);

                        }
                    }

                });
                return appointments;
    }
    public ArrayList<appointment> getCurrentUsersAppoinmentWithStatusPending(TaskCompletedCallBack callBack) {
        ArrayList<appointment> appointments = new ArrayList<appointment>();
        FirebaseFirestore.getInstance().collection("Appointments").whereEqualTo("customerId", customer.getLoggedinUser().getUId()).whereEqualTo("status","Incoming")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("appoinments", "getting appoinemtns details");
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                appointments.add(new appointment(document.getId(), document.getString("date"), document.getString("time"), document.getString("carType"), document.getString("carPlateNo"), document.getString("location"), document.getString("price"), document.getString("customerId"), document.getString("inchargeContratorName"), document.getString("status")));
                                Log.d("appoinments", "date"+document.getData());
                            }
                            callBack.isTaskCompleted(true);

                        }
                    }

                }
                );
        return appointments;
    }
    public ArrayList<appointment> getCurrentUsersAppoinmentWithStatusCompleted(TaskCompletedCallBack callBack) {
        ArrayList<appointment> appointments = new ArrayList<appointment>();
        FirebaseFirestore.getInstance().collection("Appointments").whereEqualTo("customerId", customer.getLoggedinUser().getUId()).whereEqualTo("status","Completed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               Log.d("appoinments", "getting appoinemtns details");
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       appointments.add(new appointment(document.getId(), document.getString("date"), document.getString("time"), document.getString("carType"), document.getString("carPlateNo"), document.getString("location"), document.getString("price"), document.getString("customerId"), document.getString("inchargeContratorName"), document.getString("status")));
                                                       Log.d("appoinments", "date"+document.getData());
                                                   }
                                                   callBack.isTaskCompleted(true);

                                               }
                                           }

                                       }
                );
        return appointments;
    }

    public void setInchargeContractor(String contractorName,TaskCompletedCallBack callBack)
    {
        setInchargeContratorName(contractorName);

        FirebaseFirestore.getInstance().collection("Appointments").document(getUUId()).update("inchargeContratorName",contractorName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callBack.isTaskCompleted(true);
            }
        });
    }
}
