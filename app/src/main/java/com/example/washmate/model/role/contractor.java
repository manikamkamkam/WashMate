package com.example.washmate.model.role;

public class contractor extends User{

    public contractor(String name,String email,String phoneNo)
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
