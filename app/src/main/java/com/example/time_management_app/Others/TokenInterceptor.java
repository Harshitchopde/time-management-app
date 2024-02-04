package com.example.time_management_app.Others;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.time_management_app.Utils.TokenManage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private Context context;

    public TokenInterceptor(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request orignalRequest = chain.request();
        // GEt  token form sharedprefrence
        String token = TokenManage.getToken(context);
        if(token!=null){
            Request newRequest = orignalRequest.newBuilder()
                    .header("Cookie","access_token="+token)
                    .build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(orignalRequest);

    }
}
