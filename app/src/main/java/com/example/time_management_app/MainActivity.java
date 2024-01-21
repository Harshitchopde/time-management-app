package com.example.time_management_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.time_management_app.Fragments.AnalysisFragment;
import com.example.time_management_app.Fragments.DailyFragment;
import com.example.time_management_app.Fragments.HomeFragment;
import com.example.time_management_app.Fragments.SettingFragment;
import com.example.time_management_app.random.VollyLibrary;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // this is for testing purpose
//        VollyLibrary vl = new VollyLibrary();
//        vl.getData(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);

    setBottomNavigationView();
    }

    private void setBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();
                if(id== R.id.navigation_home){
                    // 1 navigation home
                    loadFrag(new HomeFragment(),0);
                }else if(id==R.id.navigation_daily){
                    // 2 navigation daily
                    loadFrag(new DailyFragment(),0);
                }else if(id==R.id.navigation_analysis) {
                    // 3 navigation analysis
                    loadFrag(new AnalysisFragment(),0);
                }else {
                    // 4 navigation setting
                    loadFrag(new SettingFragment(),0);

                }
            return true;
            }
        });
    }

    private void loadFrag(Fragment fragment, int flag) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(flag==1){
            fragmentTransaction.add(R.id.frame_laout,fragment);
        }
        else{
            fragmentTransaction.replace(R.id.frame_laout,fragment);
            //  clean the back stack of the fragment
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}