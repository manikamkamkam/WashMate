package com.example.washmate.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.washmate.model.role.User;
import com.example.washmate.model.role.contractor;
import com.example.washmate.view.contractor.withDraw;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WithDrawRequest {
    String UUID;
    Double withDrawAmount;
    String withDrawDesc;
    String status;
    String contId;
    String adminId;

    contractor cont = contractor.getLoggedinUser();

    public WithDrawRequest() {

    }


    public WithDrawRequest(Double withDrawAmount, String withDrawDesc,String contId) {

        this.withDrawAmount = withDrawAmount;
        this.withDrawDesc = withDrawDesc;
        this.status = "Pending Approval";
        this.contId = contId;

    }

    public WithDrawRequest(String UUID, Double withDrawAmount, String withDrawDesc, String status, String contId, String adminId) {
        this.UUID = UUID;
        this.withDrawAmount = withDrawAmount;
        this.withDrawDesc = withDrawDesc;
        this.status = status;
        this.contId = contId;
        this.adminId = adminId;
    }

    public void setStatus()
    {

    }
    @Exclude
    public String getUUID() {
        return UUID;
    }

    public Double getWithDrawAmount() {
        return withDrawAmount;
    }

    public String getWithDrawDesc() {
        return withDrawDesc;
    }

    public String getStatus() {
        return status;
    }

    public String getContId() {
        return contId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void SavetoFirebase(onTaskCompletedCallBack callBack)
    {
        CollectionReference withdrawrequest = FirebaseFirestore.getInstance().collection("WithDrawalRequest");

        withdrawrequest.add(this).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String UUId = documentReference.getId();
                setUUID(UUId);
                contractor.getLoggedinUser().balanceWithdrawed(getWithDrawAmount());
                callBack.isTaskCompleted(true);
            }
        });
    }
    public ArrayList<WithDrawRequest> getAllRequest(onTaskCompletedCallBack callBack)
    {
        ArrayList<WithDrawRequest> WR = new ArrayList<WithDrawRequest>();
        FirebaseFirestore.getInstance().collection("WithDrawalRequest").whereEqualTo("contId",cont.getUId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                WR.add(new WithDrawRequest(document.getId(),document.getDouble("withDrawAmount"),document.getString("withDrawDesc"), document.getString("status"), document.getString("contId"),document.getString("adminId")));

                            }
                            callBack.isTaskCompleted(true);

                        }
                    }

                });
        return WR;
    }
    public interface onTaskCompletedCallBack
    {
        public void isTaskCompleted(boolean taskCompleted);
    }
}
