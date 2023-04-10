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
        Date currentDate = Calendar.getInstance().getTime();
        String expireDate = soldBatteryModel.getEnd_time();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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


        holder.batteryItemLayoutBinding.soldBatteryBarcode.setText(soldBatteryModel.getProduct_id());
        holder.batteryItemLayoutBinding.soldBatteryCompanyName.setText(soldBatteryModel.getSeller());
        holder.batteryItemLayoutBinding.soldBatteryDateTV.setText(soldBatteryModel.getSell_date());


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

