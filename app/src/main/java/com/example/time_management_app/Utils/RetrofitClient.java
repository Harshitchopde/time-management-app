package com.example.time_management_app.Utils;

import static com.example.time_management_app.Utils.Utils.BASE_URL;

import android.content.Context;

import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.Others.TokenInterceptor;

import java.net.CookieManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private  static   OkHttpClient okHttpClient;
    private static  synchronized OkHttpClient getOkHttpClient(Context context){
        if(okHttpClient==null){
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(new CookieManager()))
                    .addInterceptor(new TokenInterceptor(context))
                    .build();

        }
        return okHttpClient;

    }

    public static Retrofit getRetrofitInstance(Context context) {
        OkHttpClient okHttpClient1 = getOkHttpClient(context);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ApiService getApiService(Context context) {
        return getRetrofitInstance(context).create(ApiService.class);
    }
}
