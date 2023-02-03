package com.example.appshopdrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Adapter.DonHang;
import com.example.appshopdrink.Adapter.DonhangList;
import com.example.appshopdrink.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LichSuDonHang extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private List<DonHang> donHangs;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_don_hang);
        recyclerView = (RecyclerView) findViewById(R.id.listdonhang);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar) findViewById(R.id.progressMenu);
        donHangs = new ArrayList<>();
        loadDonhang();
    }

    private void loadDonhang() {
        String a = Common.url + "/AppShopTraSua/getdonhang.php";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                DonHang donHang = new DonHang();
                                donHang.cmt = object.getString("cmt");
                                donHang.date = object.getString("date");
                                donHang.id = object.getString("id");
                                donHang.name = object.getString("name");
                                donHang.detailorder = object.getString("detail");
                                donHang.diachi = object.getString("address");
                                donHang.phone = object.getString("phone");
                                donHang.Tongtien= object.getInt("tongtien");
                                donHang.status = object.getString("status");
                                if(donHang.status.equals("Giao thành công")){
                                    donHangs.add(donHang);
                                }
                            }
                        }
                        catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new DonhangList(LichSuDonHang.this,donHangs);
                        recyclerView.setAdapter(adapter);
                        recyclerView.invalidate();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", Common.phoneUser);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }
}