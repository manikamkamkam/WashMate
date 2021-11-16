package com.example.washmate.model.role;

public class administrator extends User{


   public administrator(String name,String email,String phoneNo)
   {
   setEmail(email);
   setFullName(name);
   setPhoneNumber(phoneNo);
   }
    @Override
    public void login()
    {
    setLoggedinUser(this);
    }

}
