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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.ActivityScanToAddBatteryBinding;
import com.example.warrantychecker.models.RetailerModel;
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
import java.util.List;
import java.util.Map;


public class ScanToAddBattery extends AppCompatActivity {
    ActivityScanToAddBatteryBinding binding;
    Boolean isScanBarcode = false;
    Boolean isDateChanged = false;
    Boolean isCompanyNameSelected = false;
    String barcodeValue;
    String selectedDate,expireDate;
    String selectedCompanyName;
    public static List<String> companyNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanToAddBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        showCompanyName();

        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });


        binding.selectDateTV.setOnClickListener(v -> {
            pickUpDate(v);
        });

        binding.registerBtn.setOnClickListener(v -> {

            if (isScanBarcode && isDateChanged && isCompanyNameSelected){
                register();
            }

        });

        binding.autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCompanyName = companyNameList.get(position);
                isCompanyNameSelected = true;
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


    //selectDate
    private void pickUpDate(View view) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int exactMonth = month+1;
                selectedDate = String.valueOf(year+"-"+exactMonth+"-"+dayOfMonth);
                binding.selectDateTV.setText(selectedDate);
                isDateChanged = true;
                addExpireDate(year,exactMonth,dayOfMonth);
            }
        }, year, month, day);
        dpd.show();
    }

    //add expire date
    private void addExpireDate(int year,int month,int dayOfMonth){
        expireDate = String.valueOf(year+"-"+(month+3)+"-"+dayOfMonth);

    }

    private void register(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.add_Battery_TO_DB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ScanToAddBattery.this, "Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("batterybarcode",barcodeValue);
                map.put("companyName",selectedCompanyName);
                map.put("sellingDate",selectedDate);
                map.put("ExpireDate",expireDate);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //showing all company name in spinner
    private void showCompanyName(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.show_all_retailer_url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RetailerModel retailerModel = new RetailerModel();
                    retailerModel.setCompanyName(jsonObject.getString("companyName"));
                    companyNameList.add(retailerModel.getCompanyName());
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.text_list_item,companyNameList);
            binding.autoCompleteText.setAdapter(arrayAdapter);
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}