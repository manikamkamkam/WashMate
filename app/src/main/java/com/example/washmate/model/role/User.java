package com.example.washmate.model.role;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.washmate.model.appointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public abstract class User{

    String Uid;
    String FullName;
    String Email;
    String PhoneNumber;



    public String getUId() {
        return Uid;
    }

    public void setUId(String userId) {
        Uid = userId;
    }




    public void loggedout()
    {
        FirebaseAuth.getInstance().signOut();
    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public abstract void setloginUser();

    public interface TaskCallback
    {
        public void taskCompleted(boolean isCompleted);
    }


   public void updateUserDetailsToFirebase(String fullName,String email,String phoneNo,TaskCallback callback)
   {
       FirebaseFirestore.getInstance().collection("Users")
               .document(this.getUId())
               .update("FullName",fullName,"PhoneNo",phoneNo)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       Log.d("updateProfile", "onSuccess: "+"Updated!");
                       setFullName(fullName);setPhoneNumber(phoneNo);
                       callback.taskCompleted(true);
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.d("updateProfile","onFailure :"+e);
           }
       });


   }


    public interface emailCallBack {
        void isEmailAvailable(boolean available);
    }
    public void updateUserEmail(String email, emailCallBack emailCallBack){
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            emailCallBack.isEmailAvailable(true);
                            setEmail(email);
                        } else {
                            emailCallBack.isEmailAvailable(false);
                            setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }

                    }
                }) ;


    }

    public interface PhoneNoCallBack
    {
        void isPhoneNoAvailable(boolean available);
    }
    public void isPhoneNoAvailable(String phoneNumber,PhoneNoCallBack phoneNoCallBack)
    {
        FirebaseFirestore.getInstance().collection("Users")
                .whereEqualTo("PhoneNo", phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean isAvailable = true;
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(!document.getId().equals(getUId()))
                                {
                                    isAvailable = false;
                                    Log.d("phoneNo", document.getId() + "uuid of current user"+ getUId()+ " => " + document.getData());
                                }

                            }
                            phoneNoCallBack.isPhoneNoAvailable(isAvailable);
                        } else {

                        }
                    }
                });
    }

}
