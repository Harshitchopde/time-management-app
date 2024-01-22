package com.example.time_management_app.API;

import org.json.JSONArray;
import org.json.JSONObject;

public interface onDataFetch {
   void onReciveData(JSONArray jsonObject);
   void onError(String message);
}
