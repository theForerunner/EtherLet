package com.example.l.EtherLet.network;

import android.content.Context;
import android.util.Log;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class VolleyRequest {
    public static void getJSONObject(int method, String url, Map<String, Object> map, final Context context, final VolleyCallback volleyCallback) {
        try {
            JSONObject params = null;
            if (map != null) {
                params = new JSONObject(map);
            }
            Log.i("VOLLEY", "url: " + url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("VOLLEY", response.toString());
                    //volleyCallback.onSuccess(response);
                    volleyCallback.onSuccess(response, context);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    volleyCallback.onFailure();
                }
            });

            VolleyRequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
