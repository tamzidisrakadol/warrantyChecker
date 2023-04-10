package com.example.warrantychecker.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.adapter.RetailerListAdapter;
import com.example.warrantychecker.databinding.ActivityScanToAddBatteryBinding;
import com.example.warrantychecker.models.RetailerModel;
import com.example.warrantychecker.models.Vendor;
import com.example.warrantychecker.repository.CaptureAct;
import com.example.warrantychecker.utility.Constraints;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScanToAddBattery extends AppCompatActivity {
    ActivityScanToAddBatteryBinding binding;
    Boolean isScanBarcode = false;
    Boolean isDateChanged = false;
    String barcodeValue;
    ArrayList<Vendor> vendor = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    String selectedDate;
    String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanToAddBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        getData();
        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id=vendor.get(i).getId();
                String code=binding.scanResultTv.getText().toString();
                Toast.makeText(getApplicationContext(), ""+code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.VENDOR, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = jsonObject.getInt("id");
                    String companyname = jsonObject.getString("Companyname");
                    String salesMan = jsonObject.getString("SalesMan");
                    String city = jsonObject.getString("City");
                    String area = jsonObject.getString("Area");
                    String phone = jsonObject.getString("Phone");
                    String createDate = jsonObject.getString("createDate");
                    vendor.add(new Vendor(id,companyname,salesMan,city,area,phone,createDate));
                    list.add(companyname);
                }
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinner.setAdapter(aa);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //Barcode scan
    private void scanBarcode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Volume up to flash on");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            binding.scanResultTv.setText(result.getContents());
            barcodeValue = result.getContents();
            isScanBarcode = true;
        }
    });

    //checking all the data are fine before upload

    //selectDa
    //register retailer info with battery barcode




}