package com.example.appshopdrink.Adapter;

import android.media.TimedText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshopdrink.R;

public class DrinkViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView pricetxt,nametxt,countsell;
    Button mcontainer;

    public DrinkViewHolder(@NonNull View itemView) {
        super(itemView);
        countsell = (TextView) itemView.findViewById(R.id.countsell);
        imageView = (ImageView) itemView.findViewById(R.id.image_products);
        nametxt = (TextView) itemView.findViewById(R.id.text_menu_products);
        pricetxt = (TextView) itemView.findViewById(R.id.price_menu_products);
        mcontainer = (Button) itemView.findViewById(R.id.btn_addproduct);
    }
}
