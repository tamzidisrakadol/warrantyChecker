package com.example.warrantychecker.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.example.warrantychecker.adapter.OnItemClickListener;
import com.example.warrantychecker.adapter.RetailerListAdapter;
import com.example.warrantychecker.databinding.ActivityReatilerListBinding;
import com.example.warrantychecker.models.RetailerModel;
import com.example.warrantychecker.utility.Constraints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetailerListActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityReatilerListBinding binding;
    RetailerListAdapter retailerListAdapter;
    List<RetailerModel> retailerModelList = new ArrayList<>();
    RetailerModel retailerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReatilerListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.retailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.retailerRecyclerView.addItemDecoration(dividerItemDecoration);
        showRetailerList();

    }

    private void showRetailerList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.show_all_retailer_url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                retailerModelList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    retailerModel= new RetailerModel();
                    retailerModel.setId(jsonObject.getInt("id"));
                    retailerModel.setCompanyName(jsonObject.getString("companyName"));
                    retailerModel.setSalesManName(jsonObject.getString("salesMan"));
                    retailerModel.setPhoneNumber(Long.parseLong(jsonObject.getString("phone")));
                    retailerModelList.add(retailerModel);
                    retailerListAdapter = new RetailerListAdapter(retailerModelList,this);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.retailerRecyclerView.setAdapter(retailerListAdapter);
            retailerListAdapter.notifyDataSetChanged();
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //delete data
    private void deleteData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.delete_Retailer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RetailerListActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                showRetailerList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","error"+error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", String.valueOf(retailerModel.getId()));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onItemClick(RetailerModel retailerModel) {

    }

    @Override
    public void onItemLongClick(RetailerModel retailerModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteData();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}