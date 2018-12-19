package com.example.l.EtherLet.network;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipartRequest extends Request<JSONObject> {

    private MultipartEntity entity = new MultipartEntity();

    private final Response.Listener<JSONObject> mListener;

    private List<File> mFileParts;
    /**
     * 单个文件
     * @param url
     * @param errorListener
     * @param listener
     * @param file
     */
    public MultipartRequest(String url,
                            File file,
                            Response.Listener<JSONObject> listener,
                            @Nullable Response.ErrorListener errorListener
                            ) {
        super(Method.POST, url, errorListener);

        mFileParts = new ArrayList<>();
        if (file != null) {
            mFileParts.add(file);
        }
        mListener = listener;
        buildMultipartEntity();
    }
    /**
     * 多个文件，对应一个key
     * @param url
     * @param errorListener
     * @param listener
     * @param files
     */
    public MultipartRequest(String url,
                            List<File> files,
                            Response.Listener<JSONObject> listener,
                            @Nullable Response.ErrorListener errorListener
                            ) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mFileParts = files;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        if (mFileParts != null && mFileParts.size() > 0) {
            for (File file : mFileParts) {
                entity.addPart(file.getName(), new FileBody(file));
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return super.getBodyContentType();
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        if (VolleyLog.DEBUG) {
            if (response.headers != null) {
                for (Map.Entry<String, String> entry : response.headers
                        .entrySet()) {
                    VolleyLog.d(entry.getKey() + "=" + entry.getValue());
                }
            }
        }

        try {
            String parsed = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(parsed), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }

    }


    /*
     * (non-Javadoc)
     *
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        return headers;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}
