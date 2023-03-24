package com.example.warrantychecker.adapter;

import com.example.warrantychecker.models.RetailerModel;

public interface OnItemClickListener {

    void onItemClick(RetailerModel retailerModel);
    void onItemLongClick(RetailerModel retailerModel);
}
