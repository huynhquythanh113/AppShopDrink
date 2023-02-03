package com.example.appshopdrink;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Adapter.CartListAdapter;
import com.example.appshopdrink.Adapter.Drink;
import com.example.appshopdrink.Adapter.DrinkAdapter;
import com.example.appshopdrink.Utils.Common;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatmonFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private List<Drink> drinks;
    private RecyclerView recyclerView,tra,daxay;
    private ProgressBar progressBar;
    private LinearLayout tra1,da_xay,trasua,listtra,listtrasua,listdaxay;
    private Boolean hidetra,hidetrasua,hidedaxay;
    private ImageView cartorder;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datmon, container, false);
        cartorder = (ImageView) view.findViewById(R.id.cart_icon);
        cartorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowCartOrder.class));
            }
        });
        hidetra = true;
        hidedaxay = true;
        hidetrasua = true;
        recyclerView = (RecyclerView) view.findViewById(R.id.list_cart_trasua);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setHasFixedSize(true);
        tra = (RecyclerView) view.findViewById(R.id.list_cart);
        tra.setLayoutManager(new GridLayoutManager(getActivity(),1));
        tra.setHasFixedSize(true);
        daxay = (RecyclerView) view.findViewById(R.id.list_cart_daxay);
        daxay.setLayoutManager(new GridLayoutManager(getActivity(),1));
        daxay.setHasFixedSize(true);
        drinks = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.progressMenu);
        tra1 = (LinearLayout)view.findViewById(R.id.hide_tra);
        tra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hidetra){
                    tra.animate()
                            .translationY(0)
                            .alpha(0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    tra.setVisibility(View.GONE);
                                }
                            });
                  //  tra.setVisibility(View.GONE);
                    hidetra = false;
                }
                else {
                    tra.animate()
                            .alpha(1f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    tra.setVisibility(View.VISIBLE);
                                }
                            });
                    hidetra = true;
                }
            }
        });
        da_xay = (LinearLayout)view.findViewById(R.id.hide_daxay);
        da_xay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hidedaxay){
                    daxay.animate()
                            .translationY(0)
                            .alpha(0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    daxay.setVisibility(View.GONE);
                                }
                            });
                    hidedaxay = false;
                }
                else {
                    daxay.animate()
                            .alpha(1f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    daxay.setVisibility(View.VISIBLE);
                                }
                            });
                    hidedaxay = true;
                }
            }
        });
        trasua = (LinearLayout)view.findViewById(R.id.hide_trasua);
        trasua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hidetrasua){
                    recyclerView.animate()
                            .translationY(0)
                            .alpha(0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    recyclerView.setVisibility(View.GONE);
                                }
                            });
                    hidetrasua = false;
                }
                else {
                    recyclerView.animate()
                            .alpha(1f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            });
                    hidetrasua = true;
                }
            }
        });
        getlistcard();
        ((ProfileActivity)getActivity()).initDB();
        ((ProfileActivity)getActivity()).updateCartCount();
        return view;
    }

    private void getlistcard() {
        String a = Common.url + "/AppShopTraSua/listcard.php";
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
                               drink.price = object.getInt("price");
                                drink.countsell = object.getInt("sell");
                                drink.id= object.getInt("id");
                                drinks.add(drink);
                            }
                        }
                        catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new CartListAdapter(getActivity(),drinks);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
        String a1 = Common.url + "/AppShopTraSua/listcarttra.php";
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, a1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        List<Drink> drinkList= new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                Drink drink = new Drink();
                                JSONObject object = array.getJSONObject(i);
                                drink.image = object.getString("image");
                                drink.name = object.getString("name");
                                drink.price = object.getInt("price");
                                drink.countsell = object.getInt("sell");
                                drink.id= object.getInt("id");
                                drinkList.add(drink);
                            }
                        }
                        catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new CartListAdapter(getActivity(),drinkList);
                        tra.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest1);
        String a2 = Common.url + "/AppShopTraSua/listcartdaxay.php";
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, a2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        List<Drink> drinks1 = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                Drink drink = new Drink();
                                JSONObject object = array.getJSONObject(i);
                                drink.image = object.getString("image");
                                drink.name = object.getString("name");
                                drink.price = object.getInt("price");
                                drink.countsell = object.getInt("sell");
                                drink.id= object.getInt("id");
                                drinks1.add(drink);
                            }
                        }
                        catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new CartListAdapter(getActivity(),drinks1);
                        daxay.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest2);
    }

}
