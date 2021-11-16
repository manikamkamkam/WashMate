package com.example.washmate.model.role;

import android.util.Log;

public class customer extends User {


    public customer(String Uid,String name,String email,String phoneNo)
    {
        setUId(Uid);
        setEmail(email);
        setFullName(name);
        setPhoneNumber(phoneNo);

    }

    @Override
    public void login() {
        setLoggedinUser(this);
        Log.d("login", "login: "+Uid);
    }



}
