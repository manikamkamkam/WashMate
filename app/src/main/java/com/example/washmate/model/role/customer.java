package com.example.washmate.model.role;

public class customer extends User {


    public customer(String name,String email,String phoneNo)
    {
        setEmail(email);
        setFullName(name);
        setPhoneNumber(phoneNo);
    }

    @Override
    public void login() {
        setLoggedinUser(this);
    }



}
