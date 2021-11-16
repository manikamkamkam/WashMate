package com.example.washmate.model.role;

public class administrator extends User{


   public administrator(String Uid,String name,String email,String phoneNo)
   {
      setUId(Uid);
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
