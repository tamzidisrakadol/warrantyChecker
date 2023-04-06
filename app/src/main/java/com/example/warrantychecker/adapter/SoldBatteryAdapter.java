package com.example.warrantychecker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantychecker.databinding.BatteryItemLayoutBinding;
import com.example.warrantychecker.models.SoldBatteryModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SoldBatteryAdapter extends RecyclerView.Adapter<SoldBatteryAdapter.ViewHolder>{
    List<SoldBatteryModel> soldBatteryModelList;
    DeleteItemClickListener deleteItemClickListener;

    public SoldBatteryAdapter(List<SoldBatteryModel> soldBatteryModelList, DeleteItemClickListener deleteItemClickListener) {
        this.soldBatteryModelList = soldBatteryModelList;
        this.deleteItemClickListener = deleteItemClickListener;
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
        Date currentDate = Calendar.getInstance().getTime();
        String expireDate = soldBatteryModel.getExpireDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatCurrentDate = sdf.format(currentDate);

        try {
            Date firstDate = sdf.parse(formatCurrentDate);
            Date secondDate = sdf.parse(expireDate);
            Long difference = Math.abs(firstDate.getTime()-secondDate.getTime()) ;
            Long differenceToDay = difference/(24*60*60*1000);
            holder.batteryItemLayoutBinding.expireDate.setText(differenceToDay+" days left");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        holder.batteryItemLayoutBinding.soldBatteryBarcode.setText(soldBatteryModel.getBatteryBarcode());
        holder.batteryItemLayoutBinding.soldBatteryCompanyName.setText(soldBatteryModel.getCompanyName());
        holder.batteryItemLayoutBinding.soldBatteryDateTV.setText(soldBatteryModel.getSoldDate());


        holder.batteryItemLayoutBinding.soldBatteryDeleteBtn.setOnClickListener(v -> {
            deleteItemClickListener.onItemDeleteClickListener(soldBatteryModel);
        });
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

