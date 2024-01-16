package com.example.time_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.time_management_app.random.VollyLibrary;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VollyLibrary vl = new VollyLibrary();
        vl.getData(this);
    }
}