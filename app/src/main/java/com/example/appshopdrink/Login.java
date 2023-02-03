package com.example.appshopdrink;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Utils.Common;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private TextInputEditText phone,pass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone =(TextInputEditText) findViewById(R.id.phoneUser);
        pass = (TextInputEditText) findViewById(R.id.passUser);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String phoneUser = phone.getText().toString();
        String passUser = pass.getText().toString();
        if(phoneUser != null && !phoneUser.isEmpty() && !phoneUser.equals("null") && passUser != null && !passUser.isEmpty() && !passUser.equals("null")) {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("Please wait ....");
            progressDialog.show();
            progressDialog.setCancelable(false);
            String a = Common.url + "/AppShopTraSua/login.php";
            StringRequest request = new StringRequest(Request.Method.POST, a,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            if (response.equalsIgnoreCase("Succesfully")) {
                                Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("phoneUser", phoneUser);
                                Ed.apply();
                                Common.phoneUser = phoneUser;
                                Common.passUser = passUser;
                                phone.getText().clear();
                                pass.getText().clear();
                                startActivity(new Intent(Login.this, ProfileActivity.class));
                            } else {
                                Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("phone", phoneUser);
                    params.put("pass", passUser);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill full", Toast.LENGTH_SHORT).show();
        }
    }
}