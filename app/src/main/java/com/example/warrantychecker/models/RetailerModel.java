package com.example.warrantychecker.models;

import java.util.Date;

public class RetailerModel {

    int id;
    String companyName,salesManName,cityName,areaName,phoneNumber,createdate;



    public RetailerModel(int id, String companyName, String salesManName, String cityName, String areaName, String phoneNumber,String createdate) {
        this.id = id;
        this.companyName = companyName;
        this.salesManName = salesManName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.phoneNumber = phoneNumber;
        this.createdate = createdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
