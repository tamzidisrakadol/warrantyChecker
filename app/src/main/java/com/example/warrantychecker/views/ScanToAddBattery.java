package com.example.warrantychecker.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.warrantychecker.databinding.ActivityScanToAddBatteryBinding;
import com.example.warrantychecker.repository.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Calendar;

public class ScanToAddBattery extends AppCompatActivity {
    ActivityScanToAddBatteryBinding binding;
    Boolean isScanBarcode = false;
    Boolean isDateChanged = false;

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
                Toast.makeText(this, "register Successfully", Toast.LENGTH_SHORT).show();
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
            isScanBarcode = true;
        }
    });

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

    private void pickUpDate(View view) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.valueOf(year+"/"+(month+1)+"/"+dayOfMonth);
                binding.selectDateTV.setText(selectedDate);
                isDateChanged = true;
            }
        }, year, month, day);
        dpd.show();
    }

    private void register(){

    }


}