package com.example.appshopdrink.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshopdrink.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    ImageView img_product;
    TextView text_menu,a;
    LinearLayout container;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        img_product = (ImageView) itemView.findViewById(R.id.image_product);
        text_menu = (TextView) itemView.findViewById(R.id.text_menu_product);
        a = (TextView) itemView.findViewById(R.id.textA);
        container = (LinearLayout) itemView.findViewById(R.id.container);
    }
}