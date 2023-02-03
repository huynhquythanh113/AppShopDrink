package com.example.appshopdrink.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshopdrink.Database.Model.Model.Cart;
import com.example.appshopdrink.R;
import com.example.appshopdrink.Utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DonhangList extends RecyclerView.Adapter<DonhangList.DonhangListViewHolder>{

    Context context;
    List<DonHang> hangs;

    public DonhangList(Context context, List<DonHang> hangs) {
        this.context = context;
        this.hangs = hangs;
    }

    @NonNull
    @Override
    public DonhangListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View item = LayoutInflater.from(context).inflate(R.layout.donhang,null);
        return new DonhangListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull DonhangListViewHolder holder, int position) {
        int a = position;
        if(hangs.get(position).getStatus().equals("Chua xac nhan")){
            holder.click.setBackgroundColor(Color.parseColor("#D98880"));
        }
        else if(hangs.get(position).getStatus().equals("Đã xác nhận")) {
            holder.click.setBackgroundColor(Color.parseColor("#7DCEA0"));
            holder.canhbao.setImageResource(R.drawable.ic_baseline_check_24);
            holder.canhbaotext.setText("Đã xác nhận");
        }
        else if(hangs.get(position).getStatus().equals("Đã lấy hàng")) {
            holder.click.setBackgroundColor(Color.parseColor("#f4a460"));
            holder.canhbao.setImageResource(R.drawable.ic_baseline_directions_bike_24);
            holder.canhbaotext.setText("Đang vận chuyển");
        }
        else {
            holder.click.setBackgroundColor(Color.parseColor("#eeeeee"));
            holder.canhbao.setImageResource(R.drawable.ic_baseline_verified_24);
            holder.canhbaotext.setText("Thành công");
        }
        holder.date.setText(hangs.get(position).getDate());
        holder.diachi.setText(hangs.get(position).getDiachi());
        holder.tong.setText(new StringBuilder("Đ").append(String.valueOf(hangs.get(position).getTongtien())));
        holder.id.setText(hangs.get(position).getId());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowdialogDonhang(a);
            }
        });
    }

    private void ShowdialogDonhang(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.thongtindonhang,null);

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        TextView ten,sdt,diachi,cmt,tongtien;
        ten = (TextView) view.findViewById(R.id.nameorder);
        ten.setText(hangs.get(position).getName());
        sdt = (TextView) view.findViewById(R.id.phoneorder);
        diachi = (TextView) view.findViewById(R.id.diachiUser);
        cmt = (TextView) view.findViewById(R.id.comment);
        tongtien = (TextView) view.findViewById(R.id.tienOrder);
        sdt.setText(hangs.get(position).getPhone());
        diachi.setText(hangs.get(position).getDiachi());
        cmt.setText(hangs.get(position).getCmt());
        tongtien.setText(String.valueOf(hangs.get(position).getTongtien()));
        List<Cart> carts= new ArrayList<>();
        RecyclerView recyclerView ;
        recyclerView = (RecyclerView) view.findViewById(R.id.cartsubmit);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setHasFixedSize(true);
        try {
            JSONArray array = new JSONArray(hangs.get(position).getDetailorder());
            for (int i =0 ; i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
             //   String image = object.getString("image");
             //   String name = object.getString("name");
              //  int price = object.getInt("price");
                Cart cartitem = new Cart();
                cartitem.link = object.getString("link");
                cartitem.name = object.getString("name");
                cartitem.amount = object.getInt("amount");
                cartitem.ice = object.getInt("ice");
                cartitem.sugar = object.getInt("sugar");
                cartitem.price = object.getInt("price");
                cartitem.toppingExtras = object.getString("toppingExtras");
                carts.add(cartitem);
        }} catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView.Adapter adapter = new CartListDonhang(context,carts);
        recyclerView.setAdapter(adapter);
        builder.setView(view);
        final AlertDialog  a = builder.create();
        a.show();
        a.getWindow().setLayout(1100, 2000);

    }

    @Override
    public int getItemCount() {
        return hangs.size();
    }

    public class DonhangListViewHolder extends RecyclerView.ViewHolder{

        LinearLayout click;
        TextView id,date,tong,diachi,canhbaotext;
        ImageView canhbao;

        public DonhangListViewHolder(@NonNull View itemView) {
            super(itemView);
            canhbaotext = (TextView)itemView.findViewById(R.id.canhbaotext);
            canhbao = (ImageView) itemView.findViewById(R.id.canhbao);
            click = (LinearLayout) itemView.findViewById(R.id.click);
            id  = (TextView) itemView.findViewById(R.id.iddonhang);
            date = (TextView) itemView.findViewById(R.id.datedonhang);
            tong = (TextView) itemView.findViewById(R.id.tongtiendonhang);
            diachi = (TextView) itemView.findViewById(R.id.diachidonhang);
        }
    }
}
