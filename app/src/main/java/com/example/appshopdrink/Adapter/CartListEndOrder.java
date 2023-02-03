package com.example.appshopdrink.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.TimedText;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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
import com.example.appshopdrink.ShowCartOrder;
import com.example.appshopdrink.Utils.Common;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartListEndOrder extends RecyclerView.Adapter<CartListEndOrder.CartListEndOrderViewHolder>{

    Context context;
    List<Cart> carts;


    public CartListEndOrder(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartListEndOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.cart_order_show,null);
        return new CartListEndOrderViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListEndOrderViewHolder holder, int position) {
        int b = position;
        Glide.with(context).load(carts.get(position).link).into(holder.imageView);
        holder.name.setText(carts.get(position).name);
        holder.topping.setText(carts.get(position).toppingExtras);
        holder.amount.setNumber(String.valueOf(carts.get(position).amount));
        DecimalFormat currency = new DecimalFormat("#,###,### VNĐ");
        String price = currency.format(carts.get(position).price);
        holder.price.setText(new StringBuilder("").append(price));
        holder.icesugar.setText(new StringBuilder("Sugar :")
                .append(carts.get(position).sugar).append("%").append(" ")
                .append("Ice :").append(carts.get(position).ice).append("%"));
        holder.amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = carts.get(b);
                cart.amount = newValue;
                cart.price = cart.price/oldValue *newValue;
                int giathaydoi = cart.price/oldValue;
                if(newValue == 0){
                    Common.PriceOrder -= cart.price;
                    Common.cartRepository.deleteCartItem(cart);
                    String b = String.valueOf(Common.PriceOrder);
                }
                else {
                    if( newValue>oldValue){
                        Common.PriceOrder += giathaydoi;
                        Common.cartRepository.updateToCart(cart);
                        String b = String.valueOf(Common.PriceOrder);
                    }
                    else {
                        Common.PriceOrder -= giathaydoi;
                        Common.cartRepository.updateToCart(cart);
                        String b = String.valueOf(Common.PriceOrder);
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class CartListEndOrderViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price,icesugar,topping;
        ElegantNumberButton amount;
        public RelativeLayout viewbackground;
        public LinearLayout viewforeground;
        public CartListEndOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView  = (ImageView) itemView.findViewById(R.id.img_cart);
            name = (TextView) itemView.findViewById(R.id.name_cart);
            price = (TextView) itemView.findViewById(R.id.price_cart);
            icesugar = (TextView) itemView.findViewById(R.id.sugarice);
            topping = (TextView) itemView.findViewById(R.id.topping_cart);
            amount = (ElegantNumberButton) itemView.findViewById(R.id.amount_cart);

            viewbackground = (RelativeLayout) itemView.findViewById(R.id.view_background);
            viewforeground = (LinearLayout) itemView.findViewById(R.id.container_cart);
        }
    }
}
