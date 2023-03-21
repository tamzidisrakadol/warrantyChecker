package com.example.warrantychecker.models;

import java.util.Date;

public class RetailerModel {
    String companyName,salesManName,cityName,areaName,panNumber,batteryBarcode,sellingDate;
    int phoneNumber;


    public RetailerModel() {
    }

    public RetailerModel(String companyName, String salesManName, String cityName, String areaName, String panNumber, String batteryBarcode, String sellingDate, int phoneNumber) {
        this.companyName = companyName;
        this.salesManName = salesManName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.panNumber = panNumber;
        this.batteryBarcode = batteryBarcode;
        this.sellingDate = sellingDate;
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalesManName() {
        return salesManName;
    }

    public void setSalesManName(String salesManName) {
        this.salesManName = salesManName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getBatteryBarcode() {
        return batteryBarcode;
    }

    public void setBatteryBarcode(String batteryBarcode) {
        this.batteryBarcode = batteryBarcode;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
