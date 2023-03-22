package com.example.warrantychecker.models;

import java.util.Date;

public class RetailerModel {
    String companyName,salesManName,cityName,areaName,panNumber;
    Long phoneNumber;


    public RetailerModel() {

    }

    public RetailerModel(String companyName, String salesManName, String cityName, String areaName, String panNumber, Long phoneNumber) {
        this.companyName = companyName;
        this.salesManName = salesManName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.panNumber = panNumber;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
