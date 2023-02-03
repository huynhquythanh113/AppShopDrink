package com.example.appshopdrink.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.appshopdrink.Database.Model.Model.Cart;
import com.example.appshopdrink.GiohangFragment;
import com.example.appshopdrink.ProfileActivity;
import com.example.appshopdrink.R;
import com.example.appshopdrink.ShowProduct;
import com.example.appshopdrink.Utils.Common;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {
    Context context;
    List<Drink> drinks;

    public DrinkAdapter(Context context, List<Drink> drinks) {
        this.context = context;
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,null);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        final Drink drink = drinks.get(position);
        int a =position;
        holder.countsell.setText(String.valueOf(drink.getCountsell()));
        Glide.with(context).load(drinks.get(position).getImage()).into(holder.imageView);
        holder.nametxt.setText(drink.getName());
        DecimalFormat currency = new DecimalFormat("#,###,### VNĐ");
        String price = currency.format(drink.getPrice());
        holder.pricetxt.setText(new StringBuilder("").append(price));
        holder.mcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAddproduct(a);
            }
        });
    }

    private void showAddproduct(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context).inflate(R.layout.add_product_layout,null);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_cart_add);
        ElegantNumberButton elegantNumberButton = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView textView = (TextView) itemView.findViewById(R.id.txt_name_add);
        TextView price = (TextView) itemView.findViewById(R.id.price_product);
        EditText editText = (EditText) itemView.findViewById(R.id.edt_comment);
        RadioButton sizeM = (RadioButton) itemView.findViewById(R.id.rdi_sizeM);
        RadioButton sizeL = (RadioButton) itemView.findViewById(R.id.rdi_sizeL);
        sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sizeOfCup = 0;
                }
            }
        });
        sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sizeOfCup =1 ;
                }
            }
        });

        RadioButton sugar100 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton sugar70 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_70);
        RadioButton sugar50 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton sugar30 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_30);
        RadioButton sugarfree = (RadioButton) itemView.findViewById(R.id.rdi_sugar_free);

        sugar30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sugar = 30;
                }
            }
        });
        sugar50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sugar = 50;
                }
            }
        });
        sugar70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sugar = 70;
                }
            }
        });
        sugar100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sugar = 30;
                }
            }
        });
        sugarfree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.sugar = 0;
                }
            }
        });
        RadioButton ice100 = (RadioButton) itemView.findViewById(R.id.rdi_ice_100);
        RadioButton ice70 = (RadioButton) itemView.findViewById(R.id.rdi_ice_70);
        RadioButton ice50 = (RadioButton) itemView.findViewById(R.id.rdi_ice_50);
        RadioButton ice30 = (RadioButton) itemView.findViewById(R.id.rdi_ice_30);
        RadioButton icefree = (RadioButton) itemView.findViewById(R.id.rdi_ice_free);

        ice30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.ice = 30;
                }
            }
        });
        ice50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.ice = 50;
                }
            }
        });
        ice70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.ice = 70;
                }
            }
        });
        ice100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.ice = 100;
                }
            }
        });
        icefree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.ice = 0;
                }
            }
        });
        RecyclerView recy_topping  = (RecyclerView) itemView.findViewById(R.id.recy_topping);
        recy_topping.setLayoutManager(new LinearLayoutManager(context));
        recy_topping.setHasFixedSize(true);
        String ur = Common.url + "/AppShopTraSua/listtopping.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ur,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String a = "";
                        List<Drink>  categoryList = new ArrayList<>();
                        MultichoiceAdapter adapter;
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0 ; i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                int price = object.getInt("price");
                                String name = object.getString("name");
                                String image = "a";
                                Drink drink = new Drink(name,image,price);
                                categoryList.add(drink);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter = new MultichoiceAdapter(context,categoryList);
                        recy_topping.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(context).add(stringRequest);
        Glide.with(context).load(drinks.get(position).getImage()).into(imageView);
        textView.setText(drinks.get(position).getName());
        price.setText(new StringBuilder("").append(drinks.get(position).getPrice()));
        builder.setView(itemView);
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "See you later.", Toast.LENGTH_SHORT).show();
                Common.sizeOfCup = -1;
                Common.toppingName = new ArrayList<>();
                Common.toppingPrice= 0;
                Common.sugar = -1;
                Common.ice = -1;
            }
        });
        final AlertDialog  a = builder.create();
        a.show();
        a.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.sizeOfCup == -1){
                    Toast.makeText(context, "Please choose size", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Common.sugar == -1){
                    Toast.makeText(context, "Please choose sugar", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Common.ice == -1){
                    Toast.makeText(context, "Please choose ice", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Common.ice !=-1 && Common.sizeOfCup != -1 && Common.sugar != -1) {
                    a.dismiss();
                    showConfirmDialog(position, elegantNumberButton.getNumber());
                }
            }
        });
    }

    private void showConfirmDialog(int position, String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context).inflate(R.layout.confirm_add_to_cart_layout,null);

        ImageView imageView1 = (ImageView) itemView.findViewById(R.id.img_product_add);
        TextView textView = (TextView) itemView.findViewById(R.id.txt_name_add_cart);
        TextView pricetext = (TextView) itemView.findViewById(R.id.txt_name_price);
        TextView icetext = (TextView) itemView.findViewById(R.id.txt_ice_price);
        TextView sugartext = (TextView) itemView.findViewById(R.id.txt_sugar_price);
        TextView topping = (TextView) itemView.findViewById(R.id.txt_topping_extras);

        Glide.with(context).load(drinks.get(position).getImage()).into(imageView1);
        textView.setText(new StringBuilder(drinks.get(position).getName()).append("x")
        .append(number)
        .append(Common.sizeOfCup == 0 ? "Size M" : "Size L").toString());
        icetext.setText(new StringBuilder("Ice : ").append(Common.ice).append("%").toString());
        sugartext.setText(new StringBuilder("Sugar : ").append(Common.sugar).append("%").toString());
        int a = 0;
        if(Common.sizeOfCup ==1){
            a=10000;
        }
        int price = (int) ((int) (drinks.get(position).getPrice()+a)* Integer.parseInt(number) + Common.toppingPrice );
        pricetext.setText(new StringBuilder("Đồng: ").append(price));
        StringBuilder toppingExtras = new StringBuilder("");


        for(String line : Common.toppingName){
            toppingExtras.append(line).append("\n");
        }
        topping.setText(toppingExtras);

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cart cartitem = new Cart();
                try{
                  //  cartitem.id = Common.cartRepository.countCartItems();
                cartitem.link = drinks.get(position).getImage();
                cartitem.name = drinks.get(position).getName();
                cartitem.amount = Integer.parseInt(number);
                cartitem.ice = Common.ice;
                cartitem.sugar = Common.sugar;
                cartitem.price = price;
                cartitem.idCart = drinks.get(position).getId();
                cartitem.toppingExtras = toppingExtras.toString();
                Common.cartRepository.insertToCart(cartitem);
            Log.d("EDMT_DEBUG" , new Gson().toJson(cartitem));
                Toast.makeText(context, "Save item to cart success", Toast.LENGTH_SHORT).show();
                 Common.sizeOfCup = -1;
                 Common.sugar = -1 ;
                 Common.toppingName= new ArrayList<>();
                 Common.ice = -1;
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setView(itemView);
        builder.show();
    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }

}
