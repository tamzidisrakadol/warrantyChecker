package com.example.warrantychecker.models;

public class SoldBatteryModel {
    int id;
    String batteryBarcode,companyName,soldDate,expireDate;

    public SoldBatteryModel(int id, String batteryBarcode, String companyName, String soldDate, String expireDate) {
        this.id = id;
        this.batteryBarcode = batteryBarcode;
        this.companyName = companyName;
        this.soldDate = soldDate;
        this.expireDate = expireDate;
    }

    public SoldBatteryModel() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatteryBarcode() {
        return batteryBarcode;
    }

    public void setBatteryBarcode(String batteryBarcode) {
        this.batteryBarcode = batteryBarcode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
