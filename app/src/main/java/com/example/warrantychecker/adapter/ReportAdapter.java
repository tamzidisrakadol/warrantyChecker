package com.example.warrantychecker.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView code, date;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.productCode);
            date = itemView.findViewById(R.id.productDate);
        }
    }
}
