package com.example.time_management_app.Interface;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/auth/signUp")
    Call<ResponseBody> signUp(@Body  Map<String, String> params);


    @POST("/api/auth/login")
    Call<ResponseBody> login(@Body Map<String, String> params);
}