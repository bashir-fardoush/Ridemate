package com.fardoushlab.ridemate.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {
    private static  Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().setLenient().create();
    private static final String BASE_URL = "http://audacityit.com/";


    private RetrofitApiClient() {
    }

    public static Retrofit getInstence(){
        if (retrofit == null){
            synchronized (RetrofitApiClient.class){
               retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
        }
        return retrofit;
    }


}
