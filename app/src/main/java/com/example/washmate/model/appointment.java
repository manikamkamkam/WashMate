package com.example.washmate.model;

public class appointment {
    String date;
    String price;
    String inchargeContratorName;
    String Status;

    public appointment(String date, String price, String inchargeContratorName, String status) {
        this.date = date;
        this.price = price;
        this.inchargeContratorName = inchargeContratorName;
        Status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInchargeContratorName() {
        return inchargeContratorName;
    }

    public void setInchargeContratorName(String inchargeContratorName) {
        this.inchargeContratorName = inchargeContratorName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
