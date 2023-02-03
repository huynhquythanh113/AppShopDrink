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

public class CartListDonhang extends RecyclerView.Adapter<CartListDonhang.CartListDonhangViewHolder>{

    Context context;
    List<Cart> carts;

    public CartListDonhang(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartListDonhangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.cartdonhang,null);
        return new CartListDonhangViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListDonhangViewHolder holder, int position) {
        Glide.with(context).load(carts.get(position).link).into(holder.imageView);
        holder.name.setText(carts.get(position).name);
        holder.topping.setText(carts.get(position).toppingExtras);
        holder.amount.setText(String.valueOf(carts.get(position).amount));
        DecimalFormat currency = new DecimalFormat("#,###,### VNĐ");
        String price = currency.format(carts.get(position).price);
        holder.price.setText(new StringBuilder("").append(price));
        holder.icesugar.setText(new StringBuilder("Sugar :")
                .append(carts.get(position).sugar).append("%").append(" ")
                .append("Ice :").append(carts.get(position).ice).append("%"));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }


    public class CartListDonhangViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price,icesugar,topping,amount;
        public RelativeLayout viewbackground;
        public LinearLayout viewforeground;
        public CartListDonhangViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView  = (ImageView) itemView.findViewById(R.id.img_cart);
            name = (TextView) itemView.findViewById(R.id.name_cart);
            price = (TextView) itemView.findViewById(R.id.price_cart);
            icesugar = (TextView) itemView.findViewById(R.id.sugarice);
            topping = (TextView) itemView.findViewById(R.id.topping_cart);
            amount = (TextView) itemView.findViewById(R.id.amount_cart);

        }
    }
}
