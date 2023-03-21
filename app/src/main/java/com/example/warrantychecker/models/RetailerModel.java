package com.example.warrantychecker.models;

import java.util.Date;

public class RetailerModel {
    String companyName,salesManName,cityName,areaName,panNumber,batteryBarcode;
    int phoneNumber;
    Date sellingDate;

    public RetailerModel() {
    }

    public RetailerModel(String companyName, String salesManName, String cityName, String areaName, String panNumber, String batteryBarcode, int phoneNumber, Date sellingDate) {
        this.companyName = companyName;
        this.salesManName = salesManName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.panNumber = panNumber;
        this.batteryBarcode = batteryBarcode;
        this.phoneNumber = phoneNumber;
        this.sellingDate = sellingDate;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(Date sellingDate) {
        this.sellingDate = sellingDate;
    }
}
