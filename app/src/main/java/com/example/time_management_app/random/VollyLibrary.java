package com.example.time_management_app.random;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class VollyLibrary {
    // Instantiate the RequestQueue.
    public void getData(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://172.27.109.39:8800/api/video/random";
        JsonObjectRequest getA = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString() );
            }
        });


// Add the request to the RequestQueue.
        queue.add(getA);
    }

}
