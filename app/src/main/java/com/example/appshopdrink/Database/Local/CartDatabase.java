package com.example.appshopdrink.Database.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appshopdrink.Database.Model.Model.Cart;
import com.example.appshopdrink.TrangchuFragment;

@Database(entities = {Cart.class},version = 2)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    private static CartDatabase instance;

    public static CartDatabase getInstance(Context context){
            if(instance == null)
                instance = Room.databaseBuilder(context,CartDatabase.class,"EDMT_DrinkShopDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration().build();
                return instance;

    }

}
