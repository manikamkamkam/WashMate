package com.example.washmate.model.role;

public abstract class User{

    String UserId;
    String FullName ="123";
    String Email;
    String PhoneNumber;

    private static User loggedinUser;


   public User()
   {

   }
    public static User getLoggedinUser() {
        return loggedinUser;
    }
    public static void setLoggedinUser(administrator user) {
       loggedinUser = user;
    }
    public static void setLoggedinUser(customer user)
    {
        loggedinUser = user;
    }
    public static void setLoggedinUser(contractor user)
    {
        loggedinUser = user;
    }

    public void loggedout()
    {
        loggedinUser = null;
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

    public abstract  void login();


}
