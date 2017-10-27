package com.asi.hopeitapp.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class HopeApi {

    private static Retrofit retrofit = null;

    static Retrofit getClient(){
        if(retrofit == null){

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .readTimeout(6, TimeUnit.SECONDS)
                    .writeTimeout(6, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://10.99.130.96:8123/api/")
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
