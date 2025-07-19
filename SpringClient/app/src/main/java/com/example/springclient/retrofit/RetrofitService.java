package com.example.springclient.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.os.Build;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private String getBaseUrl() {
        // Pentru emulator
        if (Build.FINGERPRINT.startsWith("generic")) {
            return "http://10.0.2.2:8080";
        }
        // Pentru dispozitiv fizic
        return "http://192.168.0.101:8080";
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
