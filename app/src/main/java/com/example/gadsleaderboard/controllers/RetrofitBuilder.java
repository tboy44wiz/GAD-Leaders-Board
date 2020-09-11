package com.example.gadsleaderboard.controllers;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static final String URL = "https://gadsapi.herokuapp.com/";

    // Create logger
    private static HttpLoggingInterceptor logger =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // Create OkHttp client
    private static OkHttpClient.Builder okHttp =
            new OkHttpClient.Builder().addInterceptor(logger);

    private static Retrofit.Builder sBuilder = new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());

    private static Retrofit retrofit = sBuilder.build();

    public static <S> S buildService(Class<S> serviceType) {
        return retrofit.create(serviceType);
    }
}
