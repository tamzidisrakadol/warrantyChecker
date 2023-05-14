package com.example.warrantychecker.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warrantychecker.R;
import com.example.warrantychecker.databinding.ActivityLoginBinding;
import com.example.warrantychecker.utility.Constraints;
import com.example.warrantychecker.utility.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;


public class LogInActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String TAG = "MyTag";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(getApplicationContext());
        progressDialog=new ProgressDialog(LogInActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        checkUser();

        getSupportActionBar().hide();
        binding.logInBtn.setOnClickListener(v -> {

            String username, password;
            username = binding.dealerEmailET.getText().toString();
            password = binding.passwordET.getText().toString();
            if (username.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.ADMIN_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse: " + response);
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String data=jsonObject.getString("data");
                        if (!data.equals("error")){
                            Paper.book().write(Keys.ID,data);
                            Toast.makeText(LogInActivity.this, "login Successfully !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                            finishAffinity();
                        }else {
                            Toast.makeText(LogInActivity.this, "error : "+data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LogInActivity.this, "" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("username", username);
                    hashMap.put("password", password);
                    return hashMap;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        });
    }

    private void checkUser() {
        if (Paper.book().read(Keys.ID)!=null){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finishAffinity();
        }
    }
}