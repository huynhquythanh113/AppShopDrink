package com.example.appshopdrink;

import static com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.RequestBuilder.delete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopdrink.Adapter.CartListEndOrder;
import com.example.appshopdrink.Adapter.CartListOrderAdapter;
import com.example.appshopdrink.Adapter.Drink;
import com.example.appshopdrink.Adapter.DrinkAdapter;
import com.example.appshopdrink.Database.Model.Model.Cart;
import com.example.appshopdrink.Utils.Common;
import com.example.appshopdrink.Utils.RecyclerItemTouchHelper;
import com.example.appshopdrink.Utils.RecyclerItemTouchHelperListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SubmitOrderActivity extends AppCompatActivity  {

    private TextView nameorder , phoneorder , tienorder , tienship , tongtien ;
    private RecyclerView cartorder;
    private CompositeDisposable compositeDisposable;
    private CartListEndOrder cartListEndOrder;
    private Button order;
    private RadioButton diachiuser,diachikhac;
    private String diachiOrder = "";
    private TextView address;
    private EditText magiamgia,cmt;
    private int Tongtien=0 ;
    List<Cart> listCart = new ArrayList<>();
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        address = (TextView) findViewById(R.id.diachiUser);
        cmt = (EditText)findViewById(R.id.comment);
        diachiuser = (RadioButton) findViewById(R.id.diachimacdinh);
        diachikhac = (RadioButton) findViewById(R.id.diachikhac);
        diachikhac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    diachiOrder = "";
                    address.setText(diachiOrder);
                }
            }
        });
        order = (Button) findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date current = Calendar.getInstance().getTime();
                String date = current.toString();
                String orderDetail = new Gson().toJson(listCart);
                String add = address.getText().toString();
                String cmt1 = cmt.getText().toString();
                String sum = String.valueOf(Tongtien);
                if(add!=null && !add.isEmpty() && !add.equals("null")){
                    final ProgressDialog progressDialog = new ProgressDialog(SubmitOrderActivity.this);
                    progressDialog.setMessage("Please wait ....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    String a = Common.url + "/AppShopTraSua/submitorder.php";
                    StringRequest request = new StringRequest(Request.Method.POST, a,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if (response.equalsIgnoreCase("Succesfully")) {
                                        Toast.makeText(SubmitOrderActivity.this, "Wish you have nice day!", Toast.LENGTH_SHORT).show();

                                        deleteCart();
                                       finish();

                                    } else {
                                        Toast.makeText(SubmitOrderActivity.this, response, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SubmitOrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("oPhone", Common.phoneUser);
                            params.put("oName", Common.nameuser);
                            params.put("oAddress", add);
                            params.put("oDetail", orderDetail);
                            params.put("oCmt", cmt1);
                            params.put("oTongtien",sum );
                            params.put("odate",date);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(SubmitOrderActivity.this);
                    requestQueue.add(request);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Pleas fill address", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tienorder = (TextView)findViewById(R.id.tienOrder);
        nameorder = (TextView) findViewById(R.id.nameorder);
        phoneorder = (TextView) findViewById(R.id.phoneorder);
        cartorder = (RecyclerView) findViewById(R.id.cartsubmit);
        cartorder.setLayoutManager(new LinearLayoutManager(this));
        cartorder.setHasFixedSize(true);
        compositeDisposable = new CompositeDisposable();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        String  a = String.valueOf(Common.PriceOrder);
        tienorder.setText(a);

        if(Common.cartRepository.countCartItems()==0){
            Toast.makeText(getApplicationContext(), "gethahaha", Toast.LENGTH_SHORT).show();
        }
        else {
            loadOrder();}

    }

    private void deleteCart() {
      Common.cartRepository.emptyCart();
    }

    private void loadDiachiuser() {
        diachiOrder = Common.address;
        address.setText(diachiOrder);
    }


    private void loadOrder() {
        diachiuser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loadDiachiuser();
                }
            }
        });
        diachikhac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    diachiOrder = "";
                    address.setText(diachiOrder);
                }
            }
        });
        nameorder.setText(Common.nameuser);
        phoneorder.setText(Common.phoneUser);
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                            @Override
                            public void accept(List<Cart> carts) throws Exception {
                                if(Common.cartRepository.countCartItems()==0){
                                    finish();
                                }
                                int tongtienorder = 0;
                                for (Cart t: carts
                                     ) {
                                    tongtienorder += t.price;
                                }
                                Tongtien = tongtienorder;
                                String  a = String.valueOf(tongtienorder);
                                tienorder.setText(a);
                                displaycartsItem(carts);
                            }
                        })
        );
    }
    private void displaycartsItem(List<Cart> carts) {
        listCart = carts;
        cartListEndOrder = new CartListEndOrder(this,carts);
        cartorder.setAdapter(cartListEndOrder);
    }
    @Override
    public void onResume() {
        if(Common.cartRepository.countCartItems()==0){
            Toast.makeText(getApplicationContext(), "gethahaha", Toast.LENGTH_SHORT).show();
        }
        else {
        loadOrder();}
        super.onResume();
    }


    private void showLocation() {

    }
}