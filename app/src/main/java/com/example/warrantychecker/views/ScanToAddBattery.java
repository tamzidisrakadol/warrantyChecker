package com.example.warrantychecker.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.warrantychecker.models.Vendor;
import com.example.warrantychecker.repository.CaptureAct;
import com.example.warrantychecker.utility.Constraints;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScanToAddBattery extends AppCompatActivity {
    ActivityScanToAddBatteryBinding binding;
    Boolean isScanBarcode = false;
    ProgressDialog progressDialog;
    String barcodeValue;
    Dialog dialog;
    ArrayList<Vendor> vendor = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    String TAG = "MyTag";
    String manualBarcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanToAddBatteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(ScanToAddBattery.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        getSupportActionBar().setTitle("Add Product");
        getData();
        binding.scanBtn.setOnClickListener(v -> {
            scanBarcode();
        });



        binding.testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                dialog=new Dialog(ScanToAddBattery.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(ScanToAddBattery.this, android.R.layout.simple_list_item_1,list);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                        // when item selected from list
                        // set selected item on textView
                        binding.testView.setText(adapter.getItem(position));


                        int id=vendor.get(position).getId();
                        manualBarcodeValue = binding.scanResultTv.getText().toString();   //manual barcode


                        binding.add.setOnClickListener(v->{
                            if (isScanBarcode && barcodeValue.length()!=0){
                                progressDialog.show();
                                sendToServer(id,barcodeValue);
                            }else if(!manualBarcodeValue.isEmpty()){
                                sendToServer(id,manualBarcodeValue);
                            }
                        });

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    private void sendToServer(int id, String code) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constraints.ADD_BATTERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(ScanToAddBattery.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ScanToAddBattery.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error",""+error.getLocalizedMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("code",code);
                hashMap.put("id",String.valueOf(id));
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
//                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
//                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                binding.spinner.setAdapter(aa);
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

}