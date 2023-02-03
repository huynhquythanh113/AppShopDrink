package com.example.appshopdrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Adapter.Drink;
import com.example.appshopdrink.Utils.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TaikhoanFragment extends Fragment {

    private Button thaydoithongtin,thongtindonhang , dangxuat , lichsudonhang;
    private TextView namedaidien , emaildaidien,sdtdaidien;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);
        namedaidien = (TextView) view.findViewById(R.id.namedaidien);
        lichsudonhang = (Button) view.findViewById(R.id.lichsudonhang);
        lichsudonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),LichSuDonHang.class));
            }
        });
        emaildaidien = (TextView) view.findViewById(R.id.emaildaidien);
        sdtdaidien = (TextView) view.findViewById(R.id.sdtdaidien);
        dangxuat = (Button) view.findViewById(R.id.dangxuat);
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity)getActivity()).dangxuat();
            }
        });
        if(Common.phoneUser !=null){
            hello(Common.phoneUser);

        }
        else {
            Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
        }

        thongtindonhang = (Button) view.findViewById(R.id.donhang);
        thongtindonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Thongtindonhang.class));
            }
        });
        thaydoithongtin = (Button) view.findViewById(R.id.thaydoithongtin);
        thaydoithongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ThayDoiThongtin.class));
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void hello(String strtext) {
        String a = Common.url + "/AppShopTraSua/uploadinfor.php";
        StringRequest request = new StringRequest(Request.Method.POST, a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String image = object.getString("image");
                                String name = object.getString("name");
                                String password = object.getString("password");
                                String address = object.getString("address");
                                Common.nameuser = name;
                                Common.address = address;
                            String a = image + name + password + address;
                            namedaidien.setText(name);
                            emaildaidien.setText(address);
                            sdtdaidien.setText(Common.phoneUser);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String , String>();
                params.put("phone",strtext);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}