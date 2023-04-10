package com.example.warrantychecker.models;

public class SoldBatteryModel {
    int id;
    String retailer_id,sell_date,product_id,seller,end_time;

    public SoldBatteryModel(int id, String retailer_id, String sell_date, String product_id, String seller, String end_time) {
        this.id = id;
        this.retailer_id = retailer_id;
        this.sell_date = sell_date;
        this.product_id = product_id;
        this.seller = seller;
        this.end_time = end_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(String retailer_id) {
        this.retailer_id = retailer_id;
    }

    public String getSell_date() {
        return sell_date;
    }

    public void setSell_date(String sell_date) {
        this.sell_date = sell_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
