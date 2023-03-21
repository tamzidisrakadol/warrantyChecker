package com.example.warrantychecker.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Dashboard");
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       binding.navDrawer.setNavigationItemSelectedListener(item -> {
           if (item.getItemId()==R.id.scanToAdd){
               Intent intent = new Intent(this,ScanToAddBattery.class);
               startActivity(intent);
           } else if (item.getItemId()==R.id.aboutUs) {
               Toast.makeText(DashboardActivity.this, "About us", Toast.LENGTH_SHORT).show();
           } else if (item.getItemId()==R.id.retailerList) {
               startActivity(new Intent(this,RetailerListActivity.class));
           }

           return true;
       });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}