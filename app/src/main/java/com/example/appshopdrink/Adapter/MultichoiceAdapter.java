package com.example.appshopdrink.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshopdrink.R;
import com.example.appshopdrink.Utils.Common;

import java.util.List;

public class MultichoiceAdapter extends RecyclerView.Adapter<MultichoiceAdapter.MultichoiceViewHolder> {

    Context context;

    List<Drink> drinks;

    public MultichoiceAdapter(Context context, List<Drink> drinks) {
        this.context = context;
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public MultichoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.multicheck_layout,null);
        return new MultichoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultichoiceViewHolder holder, int position) {
        int a = position;
        holder.checkBox.setText(drinks.get(position).getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.toppingName.add(buttonView.getText().toString());
                    Common.toppingPrice+=Integer.parseInt(String.valueOf(drinks.get(a).getPrice()));
                }
                else {
                    Common.toppingName.remove(buttonView.getText().toString());
                    Common.toppingPrice-=Integer.parseInt(String.valueOf(drinks.get(a).getPrice()));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    class MultichoiceViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;

        public MultichoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_topping);
        }
    }

}
