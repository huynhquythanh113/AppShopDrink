package com.example.appshopdrink.Retrofit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class retrofitclient {
    private static Retrofit retrofit = null;

    public static  Retrofit getClient(String baseURL){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        }
        return retrofit;
    }
}
