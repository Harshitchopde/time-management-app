package com.example.time_management_app;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.time_management_app.Fragments.AnalysisFragment;
import com.example.time_management_app.Fragments.DailyFragment;
import com.example.time_management_app.Fragments.HomeFragment;
import com.example.time_management_app.Fragments.SettingFragment;
import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.Utils.FragLoad;
import com.example.time_management_app.Utils.RetrofitClient;
import com.example.time_management_app.Utils.Utils;
import com.example.time_management_app.random.VollyLibrary;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentManager fragmentManager =getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTodayDate();
        frameLayout = findViewById(R.id.frame_laout);
        FragLoad.loadFrag(new HomeFragment(),frameLayout,fragmentManager,1);
        // this is for testing purpose
//        VollyLibrary vl = new VollyLibrary();
//        vl.getData(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);

    setBottomNavigationView();
    }

    private void createTodayDate() {
        ApiService apiService = RetrofitClient.getApiService(this);
        String todaysDate = Utils.getTodayDate();
        Map<String,String> mp= new HashMap<>();
        mp.put("date",todaysDate);
        Call<ResponseBody> call = apiService.createDate(mp);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String responseBody = response.body().string();
                        Log.e(TAG, "onResponse: "+responseBody );
                        Toast.makeText(MainActivity.this, "Created SuccessFull", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        Log.e(TAG, "onResponse:fasdkjfs "+response.errorBody().string() );
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: "+e.toString() );
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: fgg"+t.toString() );
            }
        });
    }

    private void setBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if(id== R.id.navigation_home){
                    // 1 navigation home
                   FragLoad.loadFrag(new HomeFragment(),frameLayout,fragmentManager,0);
                }else if(id==R.id.navigation_daily){
                    // 2 navigation daily
                    FragLoad.loadFrag(new DailyFragment(),frameLayout,fragmentManager,0);
                }else if(id==R.id.navigation_analysis) {
                    // 3 navigation analysis
                    FragLoad.loadFrag(new AnalysisFragment(),frameLayout,fragmentManager,0);
                }else {
                    // 4 navigation setting
                    FragLoad.loadFrag(new SettingFragment(),frameLayout,fragmentManager,0);

                }
            return true;
            }
        });
    }


}