package com.example.warrantychecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.RetailerItemLayoutBinding;
import com.example.warrantychecker.models.RetailerModel;

import java.util.List;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.Viewholder> {
    List<RetailerModel> retailerModelList;
    OnItemClickListener onItemClickListener;

    public RetailerListAdapter(List<RetailerModel> retailerModelList, OnItemClickListener onItemClickListener) {
        this.retailerModelList = retailerModelList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RetailerItemLayoutBinding retailerItemLayoutBinding = RetailerItemLayoutBinding.inflate(layoutInflater,parent,false);
        return new Viewholder(retailerItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        RetailerModel retailerModel = retailerModelList.get(position);
        String phoneNUmber = String.valueOf(retailerModel.getPhoneNumber());
        holder.retailerItemLayoutBinding.companyNameTV.setText(retailerModel.getCompanyName());
        holder.retailerItemLayoutBinding.phoneNumberTV.setText(phoneNUmber);
        holder.retailerItemLayoutBinding.salesManNameTV.setText(retailerModel.getSalesManName());
        holder.retailerItemLayoutBinding.getRoot().setOnClickListener(v -> {
            onItemClickListener.onItemClick(retailerModel);
        });

        holder.retailerItemLayoutBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLongClick(retailerModel);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return retailerModelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        RetailerItemLayoutBinding retailerItemLayoutBinding;
        public Viewholder( RetailerItemLayoutBinding retailerItemLayoutBinding) {
            super(retailerItemLayoutBinding.getRoot());
            this.retailerItemLayoutBinding=retailerItemLayoutBinding;
        }
    }
}
