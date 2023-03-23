package com.example.warrantychecker.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.adapter.RetailerListAdapter;
import com.example.warrantychecker.databinding.ActivityReatilerListBinding;
import com.example.warrantychecker.models.RetailerModel;
import com.example.warrantychecker.utility.Constraints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RetailerListActivity extends AppCompatActivity {
    ActivityReatilerListBinding binding;
    RetailerListAdapter retailerListAdapter;
    List<RetailerModel> retailerModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReatilerListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.retailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.retailerRecyclerView.addItemDecoration(dividerItemDecoration);
        showRetailerList();

    }

    private void showRetailerList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.show_all_retailer_url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RetailerModel retailerModel = new RetailerModel();
                    retailerModel.setCompanyName(jsonObject.getString("companyName"));
                    retailerModel.setSalesManName(jsonObject.getString("salesMan"));
                    retailerModel.setPhoneNumber(Long.parseLong(jsonObject.getString("phone")));
                    retailerModelList.add(retailerModel);

                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            retailerListAdapter = new RetailerListAdapter(retailerModelList);
            binding.retailerRecyclerView.setAdapter(retailerListAdapter);
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}