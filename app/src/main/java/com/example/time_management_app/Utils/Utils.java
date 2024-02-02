package com.example.time_management_app.Utils;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
//    public static final String BASE_URL = "https://time-management-app-40fu.onrender.com";
    public static final String BASE_URL= "http://192.168.137.204";

    public static String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mounth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());

        return formattedDate;
    }
}
