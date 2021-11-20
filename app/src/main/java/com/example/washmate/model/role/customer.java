package com.example.washmate.model.role;

import com.example.washmate.model.appointment;
import com.example.washmate.model.car;
import com.google.firebase.firestore.FirebaseFirestore;

public class customer extends User {
    static customer LoggedinUser;

   FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public customer(String Uid,String name,String email,String phoneNo)
    {
        super(fname, age, email);
        setUId(Uid);
        setEmail(email);
        setFullName(name);
        setPhoneNumber(phoneNo);

    }


    public void setloginUser() {
        LoggedinUser = this;
    }

    public static customer getLoggedinUser()
    {
        return LoggedinUser;
    }



    public void makeAppointment(String date, String time, String carType, String carPlateNo, String location, String price, String customerId)
    {
    appointment appointment = new appointment(date,time,carType,carPlateNo,location,price,customerId);
    appointment.saveAppointmentToFirebase();
    }

    public void addCars(String PlateNumber,String carType,String note,car.carCallBack call)
    {
        car car = new car(PlateNumber, carType, LoggedinUser.getUId(),note);
        car.addCartoFirebase(call);
    }





}
