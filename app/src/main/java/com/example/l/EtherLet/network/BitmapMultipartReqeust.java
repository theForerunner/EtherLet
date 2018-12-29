package com.example.l.EtherLet.network;

import android.graphics.Bitmap;

import com.android.volley.Response;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class BitmapMultipartReqeust{
    private final String mimeType = "form-data;";
    private static MultipartRequest multipartRequest;

    public BitmapMultipartReqeust(String url, Bitmap bitmap, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        multipartRequest = new MultipartRequest(url, null, mimeType, getImageBytes(bitmap), listener, errorListener);
    }

    public static MultipartRequest getMultipartRequest() {
        return multipartRequest;
    }

    private byte[] getImageBytes(Bitmap bmp){
        if(bmp==null)return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        return baos.toByteArray();
    }

}
