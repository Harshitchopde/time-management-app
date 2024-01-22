package com.example.time_management_app.API;

import org.json.JSONArray;
import org.json.JSONObject;

public interface onDataFetch {
   void onReciveArrayData(JSONArray jsonObject);
   void onReciveObjectData(JSONObject jsonObject);
   void onError(String message);
}
