package com.example.warrantychecker.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.databinding.ActivityScanToAddBatteryBinding;
import com.example.warrantychecker.repository.CaptureAct;
import com.example.warrantychecker.utility.Constraints;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ScanToAddBattery extends AppCompatActivity {
    ActivityScanToAddBatteryBinding binding;
    Boolean isScanBarcode = false;
    Boolean isDateChanged = false;
    String barcodeValue;
    String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanToAddBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });

        binding.selectDateTV.setOnClickListener(v -> {
            pickUpDate(v);
        });

        binding.registerBtn.setOnClickListener(v -> {

            if (isDataValid() && isScanBarcode && isDateChanged) {
                register();
            }else{
                Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }

        });


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

    //selectDate
    private void pickUpDate(View view) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = String.valueOf(year+"/"+(month+1)+"/"+dayOfMonth);
                binding.selectDateTV.setText(selectedDate);
                isDateChanged = true;
            }
        }, year, month, day);
        dpd.show();
    }


    //register retailer info with battery barcode
    private void register(){
        String companyName = binding.companyNameET.getText().toString();
        String salesman = binding.salesManET.getText().toString();
        String cityName = binding.cityNameEt.getText().toString();
        String areaName = binding.cityNameEt.getText().toString();
        String phone = binding.phoneNumberEt.getText().toString();
        String panNumber = binding.panNumberET.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.retailer_register_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(ScanToAddBattery.this, "successful", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Log.d("tag","error"+error)){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("companyName",companyName);
                map.put("salesMan",salesman);
                map.put("cityName",cityName);
                map.put("areaName",areaName);
                map.put("phone",phone);
                map.put("panNumber",panNumber);
                map.put("sellingDate", selectedDate);
                map.put("batteryBarcode",barcodeValue);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}