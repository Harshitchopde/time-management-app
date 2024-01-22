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
import org.json.JSONObject;

public class FastAndroidNetworking {
    public void getFetchData(Context context, onDataFetch onDataFetch){

        AndroidNetworking.get("http://172.27.109.39:8800/api/video/trend")

                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "onResponse: phele ye chala" );
                        onDataFetch.onReciveData(response);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        onDataFetch.onError(error.getMessage());
                    }
                });


    }
}
