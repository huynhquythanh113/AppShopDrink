package com.example.appshopdrink.Utils;

import com.example.appshopdrink.Database.Datasoure.CartRepository;
import com.example.appshopdrink.Database.Local.CartDatabase;
import com.example.appshopdrink.Retrofit.IDrinkShopAPI;
import com.example.appshopdrink.Retrofit.retrofitclient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Retrofit;

public class Common {
    private static final String BASE_URL = "http://10.0.2.2/appshopdrink";
    public static IDrinkShopAPI getAPI(){
        return retrofitclient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
    public static double toppingPrice=0;
    public static List<String> toppingName = new ArrayList<>();

    public static int sizeOfCup = -1;
    public  static int sugar = -1;
    public static int ice = -1;

    public static String phoneUser ;
    public static String address;
    public static String nameuser;
    public  static String passUser;
    public static int PriceOrder = 0;
    public  static CartDatabase cartDatabase;
    public  static CartRepository cartRepository;
    public static String url = "http://192.168.1.13";
}
