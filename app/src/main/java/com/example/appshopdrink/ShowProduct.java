package com.example.appshopdrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.appshopdrink.Adapter.Category;
import com.example.appshopdrink.Adapter.CategoryAdapter;
import com.example.appshopdrink.Adapter.Drink;
import com.example.appshopdrink.Adapter.DrinkAdapter;
import com.example.appshopdrink.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProduct extends AppCompatActivity {

    private List<Drink> categoryList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        Intent intent = getIntent();
        categoryList = new ArrayList<>();
        String id = intent.getStringExtra("id");
        progressBar = (ProgressBar) findViewById(R.id.process_product);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_product);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        getProducts(id);

    }

    private void getProducts(String id) {
        String a = Common.url + "/AppShopTraSua/getproduct.php";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                Drink drink = new Drink();
                                JSONObject object = array.getJSONObject(i);
                                drink.image = object.getString("image");
                                drink.name = object.getString("name");
                                drink.price= object.getInt("price");
                                drink.countsell = object.getInt("sell");
                                drink.id= object.getInt("id");
                                categoryList.add(drink);
                            }
                        }
                        catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ShowProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new DrinkAdapter(ShowProduct.this,categoryList);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ShowProduct.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String , String>();
                params.put("menuid",id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }


}