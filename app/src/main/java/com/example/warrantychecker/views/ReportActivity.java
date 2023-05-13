package com.example.warrantychecker.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.adapter.ReportAdapter;
import com.example.warrantychecker.databinding.ActivityReportBinding;
import com.example.warrantychecker.models.ReportModle;
import com.example.warrantychecker.utility.Constraints;
import com.example.warrantychecker.utility.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class ReportActivity extends AppCompatActivity {

    ActivityReportBinding binding;
    ArrayList<ReportModle> reportModleArrayList = new ArrayList<>();
    ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Battery Report");
        Paper.init(getApplicationContext());
        binding.reportRecyclerView.setHasFixedSize(true);
        binding.reportRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData();
        //loadProfile();
    }


    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.GET_MSG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if (!response.equals("0")){
                   try {
                       JSONArray jsonArray = new JSONArray(response);
                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           int id = jsonObject.getInt("id");
                           String vendorID = jsonObject.getString("vendorid");
                           String code = jsonObject.getString("batterycode");
                           String date = jsonObject.getString("date");
                           String report = jsonObject.getString("smg");
                           String status = jsonObject.getString("status");
                           reportModleArrayList.add(new ReportModle(id, vendorID, code, date, report,status));
                       }
                       reportAdapter = new ReportAdapter(getApplicationContext(), reportModleArrayList);
                       binding.reportRecyclerView.setAdapter(reportAdapter);
                       binding.reportRecyclerView.scrollToPosition(reportModleArrayList.size() - 1);
                       reportAdapter.notifyDataSetChanged();
                   } catch (JSONException e) {
                       throw new RuntimeException(e);
                   }
               }else{
                   Toast.makeText(ReportActivity.this, "Empty List !", Toast.LENGTH_SHORT).show();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}