package com.example.warrantychecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantychecker.databinding.BatteryItemLayoutBinding;
import com.example.warrantychecker.models.SoldBatteryModel;

import java.util.List;

public class SoldBatteryAdapter extends RecyclerView.Adapter<SoldBatteryAdapter.ViewHolder>{
    List<SoldBatteryModel> soldBatteryModelList;

    public SoldBatteryAdapter(List<SoldBatteryModel> soldBatteryModelList) {
        this.soldBatteryModelList = soldBatteryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BatteryItemLayoutBinding batteryItemLayoutBinding = BatteryItemLayoutBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(batteryItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoldBatteryModel soldBatteryModel = soldBatteryModelList.get(position);
        holder.batteryItemLayoutBinding.soldBatteryBarcode.setText(soldBatteryModel.getBatteryBarcode());
        holder.batteryItemLayoutBinding.soldBatteryCompanyName.setText(soldBatteryModel.getCompanyName());
        holder.batteryItemLayoutBinding.soldBatteryDateTV.setText(soldBatteryModel.getSoldDate());
    }

    @Override
    public int getItemCount() {
        return soldBatteryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        BatteryItemLayoutBinding batteryItemLayoutBinding;
        public ViewHolder(BatteryItemLayoutBinding batteryItemLayoutBinding) {
            super(batteryItemLayoutBinding.getRoot());
            this.batteryItemLayoutBinding=batteryItemLayoutBinding;
        }
    }
}
