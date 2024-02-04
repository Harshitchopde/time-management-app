package com.example.time_management_app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManage {
    private static final String PREF_NAME = "time-management-app";
    private static final String KEY_TOKEN = "access_token";
    public static void saveToken(Context context,String token){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
    }
    public static String getToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return preferences.getString(KEY_TOKEN,null);
    }
}
