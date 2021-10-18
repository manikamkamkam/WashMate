package com.example.washmate.model;

public class car {

    String carplateNum ="WFL 6303";
    String CarType = "suv";

    public String getCarplateNum() {
        return carplateNum;
    }

    public void setCarplateNum(String carplateNum) {
        this.carplateNum = carplateNum;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public car(String carplateNum, String carType) {
        this.carplateNum = carplateNum;
        CarType = carType;
    }
}
