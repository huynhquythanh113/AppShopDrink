package com.example.appshopdrink;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Utils.Common;

import java.util.HashMap;
import java.util.Map;

public class ThayDoiThongtin extends AppCompatActivity {

    private EditText sdt,name,add,passchange,pass,passA,newpass;
    private Button thaydoi,thaydoimatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_thongtin);
        sdt = (EditText) findViewById(R.id.sdtUser);
        sdt.setText(Common.phoneUser);
        name = (EditText) findViewById(R.id.nameUser);
        name.setText(Common.nameuser);
        add = (EditText) findViewById(R.id.addressUser);
        add.setText(Common.address);
        passchange = (EditText) findViewById(R.id.passUser);
        pass = (EditText) findViewById(R.id.passwordUser);
        passA = (EditText) findViewById(R.id.passwordAUser);
        newpass = (EditText) findViewById(R.id.newpassUser);
        thaydoi = (Button) findViewById(R.id.btnChange);
        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt1 = sdt.getText().toString();
                String name1 = name.getText().toString();
                String add1 = add.getText().toString();
                String pass1 = passchange.getText().toString();
                if(!sdt1.isEmpty() && !sdt1.equals("null") && sdt1!=null&&!name1.isEmpty() && !name1.equals("null") && name1!=null
                &&!add1.isEmpty() && !add1.equals("null") && add1!=null && !pass1.isEmpty() && !pass1.equals("null") && pass1!=null  ){
                    if(pass1 == Common.passUser || pass1.equals(Common.passUser )){
                        final ProgressDialog progressDialog = new ProgressDialog(ThayDoiThongtin.this);
                        progressDialog.setMessage("Please wait ....");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        String a = Common.url + "/AppShopTraSua/thaydoithongtin.php";
                        StringRequest request = new StringRequest(Request.Method.POST, a,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        if (response.equalsIgnoreCase("Succesfully")) {
                                            Toast.makeText(ThayDoiThongtin.this, response, Toast.LENGTH_SHORT).show();
                                            Common.nameuser= name1;
                                            Common.address = add1;
                                            Common.passUser = pass1;
                                            passchange.getText().clear();
                                        } else {
                                            Toast.makeText(ThayDoiThongtin.this, response, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(ThayDoiThongtin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("phone", sdt1);
                                params.put("name", name1);
                                params.put("add", add1);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(ThayDoiThongtin.this);
                        requestQueue.add(request);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Mat khau khong dung", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill full", Toast.LENGTH_SHORT).show();
                }
            }
        });
        thaydoimatkhau = (Button) findViewById(R.id.btnChangepass);
        thaydoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = pass.getText().toString();
                String passA1 = passA.getText().toString();
                String newpass1 = newpass.getText().toString();
                String sdt1 = sdt.getText().toString();
                if ( !pass1.isEmpty() && !pass1.equals("null") && pass1!=null&&
                        !passA1.isEmpty() && !passA1.equals("null") && passA1!=null&&!newpass1.isEmpty() && !newpass1.equals("null") && newpass1!=null  ) {
                    if(pass1 == passA1 || pass1.equals(passA1)){
                    if(pass1 == Common.passUser || pass1.equals(Common.passUser)){
                        final ProgressDialog progressDialog = new ProgressDialog(ThayDoiThongtin.this);
                        progressDialog.setMessage("Please wait ....");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        String a = Common.url + "/AppShopTraSua/thaydoimatkhau.php";
                        StringRequest request = new StringRequest(Request.Method.POST, a,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        if (response.equalsIgnoreCase("Succesfully")) {
                                            Toast.makeText(ThayDoiThongtin.this, response, Toast.LENGTH_SHORT).show();
                                            Common.passUser = newpass1;
                                            pass.getText().clear();
                                            passA.getText().clear();
                                            newpass.getText().clear();
                                        } else {
                                            Toast.makeText(ThayDoiThongtin.this, response, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(ThayDoiThongtin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("phone", sdt1);
                                params.put("pass", newpass1);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(ThayDoiThongtin.this);
                        requestQueue.add(request);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Mat khau khong dung", Toast.LENGTH_SHORT).show();
                    }}
                    else {
                        Toast.makeText(getApplicationContext(), "Nhap lai mat khau khong dung", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill full", Toast.LENGTH_SHORT).show();
                }
            }
        });
        load();
    }

    private void load() {
        sdt.setText(Common.phoneUser);
        name.setText(Common.nameuser);
        add.setText(Common.address);
    }

    @Override
    protected void onResume() {
        load();
        super.onResume();
    }
}