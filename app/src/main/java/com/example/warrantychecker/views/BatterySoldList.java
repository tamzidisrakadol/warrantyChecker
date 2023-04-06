package com.example.warrantychecker.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.adapter.DeleteItemClickListener;
import com.example.warrantychecker.adapter.SoldBatteryAdapter;
import com.example.warrantychecker.databinding.ActivityBatterySoldListBinding;
import com.example.warrantychecker.models.SoldBatteryModel;
import com.example.warrantychecker.utility.Constraints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatterySoldList extends AppCompatActivity {
    ActivityBatterySoldListBinding binding;
    SoldBatteryAdapter soldBatteryAdapter;
    SoldBatteryModel soldBatteryModel;
    List<SoldBatteryModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBatterySoldListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        showSoldBatteryList();
        binding.batterySellRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.batterySellRecyclerView.addItemDecoration(dividerItemDecoration);

    }



    //showing all batteryList
    private void showSoldBatteryList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.show_all_sold_battery, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    modelList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        soldBatteryModel = new SoldBatteryModel();
                        soldBatteryModel.setId(jsonObject.getInt("id"));
                        soldBatteryModel.setBatteryBarcode(jsonObject.getString("batterybarcode"));
                        soldBatteryModel.setCompanyName(jsonObject.getString("companyName"));
                        soldBatteryModel.setSoldDate(jsonObject.getString("sellingDate"));
                        soldBatteryModel.setExpireDate(jsonObject.getString("ExpireDate"));
                        modelList.add(soldBatteryModel);
                        soldBatteryAdapter = new SoldBatteryAdapter(modelList, new DeleteItemClickListener() {
                            @Override
                            public void onItemDeleteClickListener(SoldBatteryModel soldBatteryModel) {
                               deleteItem();
                            }
                        });
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                binding.batterySellRecyclerView.setAdapter(soldBatteryAdapter);
                soldBatteryAdapter.notifyDataSetChanged();
            }
        }, error -> Log.d("tag", "error" + error));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //delete item from list
    private void deleteItem(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.delete_sold_battery, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(BatterySoldList.this, "Deleted", Toast.LENGTH_SHORT).show();
                showSoldBatteryList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(soldBatteryModel.getId()));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}