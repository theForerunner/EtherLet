package com.example.l.EtherLet.network;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MultipartRequest extends Request<JSONObject> {
    private static final String PROTOCOL_CHARSET = "utf-8";
    private final Response.Listener<JSONObject> mListener;
    private final Map<String, String> mParams;
    private final String mMimeType;
    private final byte[] mMultipartBody;

    public MultipartRequest(String url,
                            Map<String, String> headers,
                            String mimeType,
                            byte[] multipartBody,
                            Response.Listener<JSONObject> listener,
                            @Nullable Response.ErrorListener errorListener
                            ) {
        this(Method.POST, url, headers,mimeType, multipartBody, listener, errorListener);
    }

    public MultipartRequest(int method, String url, Map<String, String> params, String mimeType, byte[] multipartBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mParams = params;
        this.mListener = listener;
        this.mMimeType = mimeType;
        this.mMultipartBody = multipartBody;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (mParams != null) ? mParams : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return mMimeType;
    }

    @Override
    public byte[] getBody(){
        return mMultipartBody;

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(
                    new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}