package com.asi.hopeitapp.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class HopeApi {

    private static Retrofit retrofit = null;

    static Retrofit getClient(){
        if(retrofit == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(interceptor)
                    .connectTimeout(9, TimeUnit.SECONDS)
                    .readTimeout(9, TimeUnit.SECONDS)
                    .writeTimeout(9, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://10.99.130.75:3000/api/")
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
