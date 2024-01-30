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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.time_management_app.Fragments.AnalysisFragment;
import com.example.time_management_app.Fragments.DailyFragment;
import com.example.time_management_app.Fragments.HomeFragment;
import com.example.time_management_app.Fragments.SettingFragment;
import com.example.time_management_app.Utils.FragLoad;
import com.example.time_management_app.Utils.Utils;
import com.example.time_management_app.random.VollyLibrary;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentManager fragmentManager =getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize the AndroidNetworking
        AndroidNetworking.initialize(getApplicationContext());
        createTodaysDetails();
        frameLayout = findViewById(R.id.frame_laout);
        FragLoad.loadFrag(new HomeFragment(),frameLayout,fragmentManager,1);
        // this is for testing purpose
//        VollyLibrary vl = new VollyLibrary();
//        vl.getData(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);

    setBottomNavigationView();
    }

    private void createTodaysDetails() {
        try{
            AndroidNetworking.post(Utils.BASE_URL+"date/createDate")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "onResponse: "+response.toString() );
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "onError: "+anError );
                        }
                    });
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.e(TAG, "onResponse: hogya bhai"+response.toString() );
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            Log.e(TAG, "onError: error"+anError.toString() );
//                        }
//                    });
        }catch (Exception e){
            Log.e(TAG, "createTodaysDetails: error hogy bhai"+e.toString() );
        }
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