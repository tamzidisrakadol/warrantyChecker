package com.example.warrantychecker.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.adapter.DeleteItemClickListener;
import com.example.warrantychecker.adapter.RetailerListAdapter;
import com.example.warrantychecker.adapter.SoldBatteryAdapter;
import com.example.warrantychecker.databinding.ActivityDashboardBinding;
import com.example.warrantychecker.models.RetailerModel;
import com.example.warrantychecker.models.SoldBatteryModel;
import com.example.warrantychecker.utility.Constraints;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    ActionBarDrawerToggle toggle;
    List<RetailerModel> retailerModelList = new ArrayList<>();
    List<SoldBatteryModel> soldBatteryModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Dashboard");
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        showTotalCustomerList();
        showTotalSoldBatteryList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.navDrawer.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.scanToAdd) {
                Intent intent = new Intent(this, ScanToAddBattery.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.addRetailer) {
                startActivity(new Intent(this, AddRetailerActivity.class));
            } else if (item.getItemId() == R.id.retailerList) {
                startActivity(new Intent(this, RetailerListActivity.class));
            } else if (item.getItemId() == R.id.batterySellingList) {
                startActivity(new Intent(this, BatterySoldList.class));
            }
            return true;
        });

    }


    @Override
    protected void onResume() {
        showTotalCustomerList();
        showTotalSoldBatteryList();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //customer list
    private void showTotalCustomerList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.show_all_retailer_url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                retailerModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RetailerModel retailerModel = new RetailerModel();
                    retailerModel.setCompanyName(jsonObject.getString("companyName"));
                    retailerModelList.add(retailerModel);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.dashboardRetailerList.setText(String.valueOf(retailerModelList.size()));

        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //battery list
    private void showTotalSoldBatteryList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.show_all_sold_battery, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                soldBatteryModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SoldBatteryModel soldBatteryModel = new SoldBatteryModel();
                    soldBatteryModel.setBatteryBarcode(jsonObject.getString("batterybarcode"));
                    soldBatteryModelList.add(soldBatteryModel);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.dashboardBatteryList.setText(String.valueOf(soldBatteryModelList.size()));

        }, error -> Log.d("tag", "error" + error));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}