package com.example.washmate.model.role;

import com.google.firebase.firestore.FirebaseFirestore;

public class contractor extends User{

    static contractor LoggedinUser;
    double balance;
    public contractor(String Uid,String name,String email,String phoneNo,double balance)
    {
        setUId(Uid);
        setEmail(email);
        setFullName(name);
        setPhoneNumber(phoneNo);
        setBalance(balance);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    public void balanceWithdrawed(double amount)
    {

        balance -= amount;
        FirebaseFirestore.getInstance().collection("Users").document(getUId()).update("Balance",balance);
    }

    public static contractor getLoggedinUser()
    {
        return LoggedinUser;
    }



    public void setloginUser() {
    LoggedinUser = this;
    }
}
