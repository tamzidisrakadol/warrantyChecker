package com.example.warrantychecker.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.ActivityLoginBinding;

public class LogInActivity extends AppCompatActivity {
    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        getSupportActionBar().hide();


        activityLoginBinding.logInBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
            finish();
        });

    }
}