package com.example.warrantychecker.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddRetailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.addRetailerBtn.setOnClickListener(v ->{
            if (isDataValid()){
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
        }
        return true;
    }


    //register retailer info with battery barcode
    private void register(){
        String companyName = binding.companyNameET.getText().toString();
        String salesman = binding.salesManET.getText().toString();
        String cityName = binding.cityNameEt.getText().toString();
        String areaName = binding.cityNameEt.getText().toString();
        String phone = binding.phoneNumberEt.getText().toString();
        String panNumber = binding.panNumberET.getText().toString();


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.retailer_register_url, response -> {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                finish();
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }, error -> Log.d("tag","error"+error)){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                map.put("companyName",companyName);
//                map.put("salesMan",salesman);
//                map.put("cityName",cityName);
//                map.put("areaName",areaName);
//                map.put("phone",phone);
//                map.put("panNumber",panNumber);
//                return map;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
    }

}