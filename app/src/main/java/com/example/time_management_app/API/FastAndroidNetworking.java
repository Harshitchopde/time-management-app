package com.example.time_management_app.API;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FastAndroidNetworking {
    String Base_URL = "http://192.168.43.33:8800/api/video/trend";
    public void getFetchData(onDataFetch onDataFetch){

        AndroidNetworking.get(Base_URL)

                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "onResponse: ye GET ka hai" );
                        onDataFetch.onReciveArrayData(response);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        onDataFetch.onError(error.getMessage());
                    }
                });


    }

    public void postFetchData( onDataFetch onDataFetch){

        AndroidNetworking.post(Base_URL)

                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "onResponse: ye POST ka Hai" );
                        onDataFetch.onReciveArrayData(response);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        onDataFetch.onError(error.getMessage());
                    }
                });


    }
    public void putFetchData( onDataFetch onDataFetch){
        String url = "http://172.27.109.39:4000/api/auth/update";
        Log.e(TAG, "putFetchData: put fatch" );
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", "firstname");
            jsonObject.put("email", "gsdfgdf@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "putFetchData: chal rha hai" );
        AndroidNetworking.put(url)
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: ye PUT ka hai" );
                        Log.e(TAG, "onResponse: "+response.toString() );
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError:PUT  "+anError.toString() );
                    }
                });
//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.e(TAG, "onResponse: ye PUT ka hai" );
//                        onDataFetch.onReciveArrayData(response);
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        onDataFetch.onError(error.getMessage());
//                    }
//                });


    }

    public void deleteFetchData(onDataFetch onDataFetch){

        AndroidNetworking.delete(Base_URL)

                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "onResponse: ye DELETE ka hai" );
                        onDataFetch.onReciveArrayData(response);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "onError: "+error );
                        onDataFetch.onError(error.getMessage());
                    }
                });


    }





}
