package com.example.washmate.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.washmate.model.role.customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

@IgnoreExtraProperties
public class car {



    String UUId;
    String carplateNum ="WFL 6303";
    String CarType = "suv";
    String CarOwnerUUId;
    String note;

    public String getCarplateNum() {
        return carplateNum;
    }

    public void setUUId(String UUId) {
        this.UUId = UUId;
    }

    public void setCarOwnerUUId(String carOwnerUUId) {
        CarOwnerUUId = carOwnerUUId;
    }

    @Exclude
    public String getUUId() {
        return UUId;
    }

    public String getCarOwnerUUId() {
        return CarOwnerUUId;
    }

    public void setCarplateNum(String carplateNum) {
        this.carplateNum = carplateNum;
    }

    public String getCarType() {
        return CarType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public car(String carplateNum, String carType,String carOwnerUUId,String note) {
        this.carplateNum = carplateNum;
        CarType = carType;
        this.CarOwnerUUId = carOwnerUUId;
        this.note = note;
    }
    public car()
    {}
    public car(String UUId, String carplateNum, String carType, String carOwnerUUId, String note) {
        this.UUId = UUId;
        this.carplateNum = carplateNum;
        CarType = carType;
        CarOwnerUUId = carOwnerUUId;
        this.note = note;
    }

    public void addCartoFirebase(carCallBack call)
    {
        CollectionReference firebaseAppoinment = FirebaseFirestore.getInstance().collection("Cars");

        firebaseAppoinment.add(this).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String UUId = documentReference.getId();
                setUUId(UUId);
                call.isTaskCompleted(true);

            }
        });
    }

    public interface carCallBack
    {
       void isTaskCompleted(boolean isCompleted);
    }


    public ArrayList<car> getCurrentUserCars(carCallBack callBack)
    {
        ArrayList<car> cars = new ArrayList<car>();
        FirebaseFirestore.getInstance().collection("Cars").whereEqualTo("carOwnerUUId",customer.getLoggedinUser().getUId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cars.add(new car(document.getId(), document.getString("carplateNum"),document.getString("carType"),document.getString("carOwnerUUId"), document.getString("note") ));
                            }
                            callBack.isTaskCompleted(true);

                        }
                    }
                });
        return cars;
    }
}
