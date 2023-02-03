package com.example.appshopdrink;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextInputEditText name,pass,address;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        firebaseAuth = FirebaseAuth.getInstance();

        name = (TextInputEditText) findViewById(R.id.nameUser);
        pass = (TextInputEditText) findViewById(R.id.passwordUser);
        address = (TextInputEditText) findViewById(R.id.addressUser);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    String phone = firebaseUser.getPhoneNumber();
                    register(phone);

                }
                else {
                    Toast.makeText(RegisterActivity2.this, "Can't find Phone number", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void register(String phone) {
        String namee  = name.getText().toString();
        String addresss  = address.getText().toString();
        String passs  = pass.getText().toString();
        String phonee = phone;

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity2.this);
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String a = Common.url + "/AppShopTraSua/airborn.php";
        StringRequest request = new StringRequest(Request.Method.POST, a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equalsIgnoreCase("Succesfully")) {
                            Toast.makeText(RegisterActivity2.this, response, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity2.this,Login.class));
                        } else {
                            Toast.makeText(RegisterActivity2.this, phone, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String , String>();
                params.put("name",namee);
                params.put("address",addresss);
                params.put("pass",passs);
                params.put("phone",phonee);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity2.this);
        requestQueue.add(request);

    }

    }
