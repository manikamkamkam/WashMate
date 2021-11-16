package com.example.washmate.model.role;

public class contractor extends User{

    public contractor(String Uid,String name,String email,String phoneNo)
    {
        setUId(Uid);
        setEmail(email);
        setFullName(name);
        setPhoneNumber(phoneNo);
    }
    @Override
    public void login() {

        setLoggedinUser(this);
    }



}
