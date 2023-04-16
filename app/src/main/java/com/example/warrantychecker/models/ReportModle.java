package com.example.warrantychecker.models;

public class ReportModle {
    int id;
    String vendorid,batterycode,date,smg;

    public ReportModle(int id, String vendorid, String batterycode, String date, String smg) {
        this.id = id;
        this.vendorid = vendorid;
        this.batterycode = batterycode;
        this.date = date;
        this.smg = smg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getBatterycode() {
        return batterycode;
    }

    public void setBatterycode(String batterycode) {
        this.batterycode = batterycode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSmg() {
        return smg;
    }

    public void setSmg(String smg) {
        this.smg = smg;
    }
}
