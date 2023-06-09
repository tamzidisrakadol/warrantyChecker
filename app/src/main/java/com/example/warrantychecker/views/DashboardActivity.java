package com.example.warrantychecker.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.warrantychecker.databinding.ActivityDashboardBinding;
import com.example.warrantychecker.utility.Constraints;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    ActionBarDrawerToggle toggle;
    String TAG = "MyTag";
    int i, x, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.navDrawer.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.scanToAdd) {
                Intent intent = new Intent(this, ScanToAddBattery.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.retailerList) {
                startActivity(new Intent(this, RetailerListActivity.class));
            } else if (item.getItemId()==R.id.batterySellingList){
                startActivity(new Intent(this, BatterySoldList.class));
            }else if (item.getItemId()==R.id.addRetailer) {
                startActivity(new Intent(getApplicationContext(), AddRetailerActivity.class));
            }else if (item.getItemId()==R.id.report) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            }

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.VENDOR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if (response.equals("0")){
                   binding.totalRetailerTV.setText("0");
               }else{

                   try {
                       JSONArray jsonArray = new JSONArray(response);
                       for (i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           Log.d(TAG, "onResponse: " + jsonObject.getString("id"));
                       }
                       binding.totalRetailerTV.setText("" + i);
                       getBattery();
                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }
               }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getBattery() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.BATTERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("0")){
                    binding.totalBatteryTV.setText("0");
                }else{

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (x = 0; x < jsonArray.length(); x++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            Log.d(TAG, "onResponse: " + jsonObject.getString("id"));
                        }
                        binding.totalBatteryTV.setText("" + x);
//
                        getWarrenty();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getWarrenty() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.WARRENTY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if (response.equals("0")){
                   binding.totalOnWarrentyTV.setText("0");
               }else{

                   try {
                       JSONArray jsonArray = new JSONArray(response);
                       for (z = 0; z < jsonArray.length(); z++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(z);
                           Log.d(TAG, "onResponse: " + jsonObject.getString("id"));
                       }
                       binding.totalOnWarrentyTV.setText("" + z);

                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }
               }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    protected void onResume() {
        getData();
        super.onResume();
    }
}