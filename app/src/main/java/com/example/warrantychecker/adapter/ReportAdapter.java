package com.example.warrantychecker.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.models.ReportModle;
import com.example.warrantychecker.utility.Constraints;
import com.example.warrantychecker.utility.Keys;
import com.example.warrantychecker.views.ReportActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.viewholder> {

    private Context context;
    private List<ReportModle> reportModles;

    public ReportAdapter(Context context, List<ReportModle> reportModles) {
        this.context = context;
        this.reportModles = reportModles;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ReportModle reportModle = reportModles.get(position);
        if (reportModle.getStatus().equals("1")){
            holder.solve.setVisibility(View.VISIBLE);
        }


        if (reportModle.getStatus().equals("1")){
            holder.status.setText("Pending !");
        }else{
            holder.status.setText("Solved !");
        }

        holder.solve.setOnClickListener(v->{
            holder.solve.setEnabled(false);
            AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
            alertbox.setTitle("Alert !");
            alertbox.setCancelable(false);
            alertbox.setMessage("do you sure problem is solved ?");
            alertbox.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Constraints.UPDATE_REPORT, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            holder.solve.setVisibility(View.GONE);
                            if (response.equals("Success")){
                                Toast.makeText(context, "Successfully Updated !", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Error "+response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            holder.solve.setEnabled(true);
                            Toast.makeText(context, ""+error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id", String.valueOf(reportModle.getId()));
                            return hashMap;
                        }
                    };
                    RequestQueue requestQueue=Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                }
            }).setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    holder.solve.setEnabled(true);

                }
            });
            alertbox.show();

        });
        holder.code.setText(reportModle.getBatterycode());
        holder.date.setText(reportModle.getDate());
        holder.itemView.setOnClickListener(v -> {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.PROFILE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        //int id = jsonObject.getInt("id");
                        String cName = jsonObject.getString("Companyname");


                        AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                        alertbox.setTitle("info !");
                        alertbox.setMessage("Retailer : "+cName+"\n"+"Submit Date : "+reportModle.getDate()+
                                " \n"+"Battery code : "+reportModle.getBatterycode()+
                                "\n"+"Message : "+reportModle.getSmg()+"\n");
                        alertbox.setNeutralButton("OK",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {

                                    }
                                });
                        alertbox.show();

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id",reportModle.getVendorid());
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);




        });
    }

    @Override
    public int getItemCount() {
        return reportModles.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView code, date,status;
        Button solve;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.productCode);
            date = itemView.findViewById(R.id.productDate);
            status = itemView.findViewById(R.id.statusTV);
            solve = itemView.findViewById(R.id.solvedBtn);
        }
    }
}
