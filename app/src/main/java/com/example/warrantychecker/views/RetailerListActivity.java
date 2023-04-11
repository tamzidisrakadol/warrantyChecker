package com.example.warrantychecker.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.adapter.OnItemClickListener;
import com.example.warrantychecker.adapter.RetailerListAdapter;
import com.example.warrantychecker.databinding.ActivityReatilerListBinding;
import com.example.warrantychecker.models.RetailerModel;
import com.example.warrantychecker.utility.Constraints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RetailerListActivity extends AppCompatActivity implements OnItemClickListener {
    ActivityReatilerListBinding binding;
    RetailerListAdapter retailerListAdapter;
    List<RetailerModel> retailerModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReatilerListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Retailers");
        binding.retailerRecyclerView.setHasFixedSize(true);
        binding.retailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        binding.retailerRecyclerView.addItemDecoration(dividerItemDecoration);
        showRetailerList();

    }

    private void showRetailerList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constraints.VENDOR, response -> {
            try {
                JSONArray  jsonArray = new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id=jsonObject.getInt("id");
                    String companyname=jsonObject.getString("Companyname");
                    String salesMan=jsonObject.getString("SalesMan");
                    String city=jsonObject.getString("City");
                    String area=jsonObject.getString("Area");
                    String phone=jsonObject.getString("Phone");
                    String createDate=jsonObject.getString("createDate");

                    retailerModelList.add(new RetailerModel(id,companyname,salesMan,city,area,phone,createDate));

                }
                retailerListAdapter = new RetailerListAdapter(retailerModelList,this);
                binding.retailerRecyclerView.setAdapter(retailerListAdapter);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemClick(RetailerModel retailerModel) {
        String msg="CompanyName : "+retailerModel.getCompanyName()+"\n"+" SalesMan : "+retailerModel.getSalesManName()+"\n"+" City : "+retailerModel.getCityName()
                +"\n"+" Area : "+retailerModel.getAreaName()+"\n"+" Phone : "+retailerModel.getPhoneNumber()+"\n"+" Account Create : "+retailerModel.getCreatedate();
        AlertDialog.Builder builder=new AlertDialog.Builder(RetailerListActivity.this);
        builder.setTitle("Info!");
        builder.setMessage(msg);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onItemLongClick(RetailerModel retailerModel) {

    }
}