package com.example.washmate.model.role;

public class administrator extends User{

   static administrator LoggedinUser;
   public administrator(String Uid,String name,String email,String phoneNo)
   {
      setUId(Uid);
   setEmail(email);
   setFullName(name);
   setPhoneNumber(phoneNo);
   }



   public void setloginUser() {
      LoggedinUser = this;
   }

   public administrator getLoggedinUser()
   {
      return LoggedinUser;
   }
}
