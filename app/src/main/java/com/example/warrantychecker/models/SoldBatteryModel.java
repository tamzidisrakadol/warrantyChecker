package com.example.warrantychecker.models;

public class SoldBatteryModel {
    int id;
    String batteryBarcode,companyName,soldDate;

    public SoldBatteryModel(int id, String batteryBarcode, String companyName, String soldDate) {
        this.id = id;
        this.batteryBarcode = batteryBarcode;
        this.companyName = companyName;
        this.soldDate = soldDate;
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
}
