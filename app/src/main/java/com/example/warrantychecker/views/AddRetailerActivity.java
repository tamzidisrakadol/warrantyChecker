package com.example.warrantychecker.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.ActivityAddRetailerBinding;
import com.example.warrantychecker.utility.Constraints;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddRetailerActivity extends AppCompatActivity {
    ActivityAddRetailerBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRetailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(AddRetailerActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        binding.addRetailerBtn.setOnClickListener(v -> {
            if (isDataValid()) {
                register();
            }
        });
    }


    private boolean isDataValid() {
        if (binding.companyNameET.getText().toString().isEmpty()) {
            binding.companyNameET.requestFocus();
            return false;
        } else if (binding.salesManET.getText().toString().isEmpty()) {
            binding.salesManET.requestFocus();
            return false;
        } else if (binding.cityNameEt.getText().toString().isEmpty()) {
            binding.cityNameEt.requestFocus();
            return false;
        } else if (binding.areaNameEt.getText().toString().isEmpty()) {
            binding.areaNameEt.requestFocus();
            return false;
        } else if (binding.phoneNumberEt.getText().toString().isEmpty()) {
            binding.phoneNumberEt.requestFocus();
            return false;
        } else if (binding.panNumberET.getText().toString().isEmpty()) {
            binding.panNumberET.requestFocus();
            return false;
        } else if (binding.secondPhone.getText().toString().isEmpty()) {
            binding.secondPhone.requestFocus();
            return false;
        } else if (binding.password.getText().toString().isEmpty()) {
            binding.password.requestFocus();
            return false;
        }
        return true;
    }


    //register retailer info with battery barcode
    private void register() {
        progressDialog.show();
        String companyName = binding.companyNameET.getText().toString();
        String salesman = binding.salesManET.getText().toString();
        String cityName = binding.cityNameEt.getText().toString();
        String areaName = binding.areaNameEt.getText().toString();
        String phone = binding.phoneNumberEt.getText().toString();
        String panNumber = binding.panNumberET.getText().toString();
        String secondPhone = binding.secondPhone.getText().toString();
        String password = binding.password.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.ADD_VENDOR, response -> {
            progressDialog.dismiss();
            if (response.toString().equals("success")){
                Toast.makeText(this, "Successfully Retailer Added !", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },2000);
            }else{
                Toast.makeText(this, "" + response, Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(this, ""+error.toString(), Toast.LENGTH_SHORT).show();
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("companyname", companyName);
                map.put("salesMan", salesman);
                map.put("city", cityName);
                map.put("area", areaName);
                map.put("phone", phone);
                map.put("PANNumber", panNumber);
                map.put("secondPhone", secondPhone);
                map.put("password", password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}