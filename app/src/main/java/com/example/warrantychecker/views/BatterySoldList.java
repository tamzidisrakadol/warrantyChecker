package com.example.warrantychecker.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.ActivityBatterySoldListBinding;

public class BatterySoldList extends AppCompatActivity {
    ActivityBatterySoldListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBatterySoldListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}