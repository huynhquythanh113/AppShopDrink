package com.example.appshopdrink.Database.Model.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "link")
    public String link;

    @ColumnInfo(name = "idCart")
    public int idCart;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "sugar")
    public int sugar;

    @ColumnInfo(name = "ice")
    public int ice;

    @ColumnInfo(name = "toppingExtras")
    public String toppingExtras;


}
