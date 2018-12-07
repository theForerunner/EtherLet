package com.example.l.EtherLet.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyRequestQueueSingleton {
    private static VolleyRequestQueueSingleton instance;
    private RequestQueue requestQueue;
    private Context context;

    private VolleyRequestQueueSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueueInstance();
    }

    public static synchronized VolleyRequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequestQueueSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueueInstance() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueueInstance().add(request);
    }
}
