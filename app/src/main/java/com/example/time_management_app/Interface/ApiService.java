package com.example.time_management_app.Interface;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;


import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/auth/signUp")
    Call<ResponseBody> signUp(@Body  Map<String, String> params);


    @POST("/api/auth/login")
    Call<ResponseBody> login(@Body Map<String, String> params);
    // GET is isAuthenticated
    @GET("/api/auth/isAuthenticated")
    Call<ResponseBody> isAuthenticated();
    // create date
    // error i pass string in body but i have to pass map in it
    @POST("/api/date/createDate")
    Call<ResponseBody> createDate(@Body Map<String,String> params);
    @GET("/api/date/getDateDetails")
    Call<ResponseBody> getDateDetails(@Query("date") String date);

    // get todays date
    @GET("/api/date/")
    Call<ResponseBody> getTodayDate();

//    /api/schedule/
    @GET("/api/schedule/find")
    Call<ResponseBody> getSchedules(@Query("date") String date);

}