package com.example.time_management_app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPreference {
    private static final String PREFS_FILE_NAME = "MyPrefs"; // Name of the SharedPreferences file

    // Method to save a string value to SharedPreferences
    public static void saveString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Method to retrieve a string value from SharedPreferences
    public static String getString(Context context,String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

}
