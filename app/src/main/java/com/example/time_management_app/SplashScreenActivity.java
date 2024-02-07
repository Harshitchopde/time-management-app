package com.example.time_management_app;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.LoginRegister.LoginActivity;
import com.example.time_management_app.Utils.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 3000;
    private static boolean IsAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        isAuthenticated();
        new Handler().postDelayed(()->{
            if(IsAuthenticated){
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
            }else{
                startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
            }

            finish(); // Finish the splash screen activity to prevent going back to it

        },SPLASH_SCREEN_DURATION);
    }
    private void isAuthenticated() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<ResponseBody> call = apiService.isAuthenticated();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: Successfull "+response.toString() );
                    Log.e(TAG, "onResponse: "+response.body() );
                    IsAuthenticated = true;

                }else{
                    try {
                        IsAuthenticated =false;

                        Log.e(TAG, "onResponse: "+response.errorBody().string() );
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: "+e.toString() );
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: galti kar rha hai "+t.toString() );
            }
        });
    }
}