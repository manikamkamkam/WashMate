package com.example.washmate.model.role;

public class contractor extends User{

    static contractor LoggedinUser;
    public contractor(String Uid,String name,String email,String phoneNo)
    {
        setUId(Uid);
        setEmail(email);
        setFullName(name);
        setPhoneNumber(phoneNo);
    }



    @Override
    public void setloginUser() {
    LoggedinUser = this;
    }
}
