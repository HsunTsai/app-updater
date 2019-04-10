package com.hsun.appupdater;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsun on 2019/4/9.
 *  
 */

public class CustomJsonObjectRequest extends JsonRequest<JSONObject> {

    private String requestName;
    private Response.Listener<JSONObject> listener;
    private String cookie = "";
    private Map<String, String> header = new HashMap<String, String>(1);

    /**
     * string data
     *
     * @param method
     * @param url
     * @param data
     * @param listener
     * @param errorListener
     */
    CustomJsonObjectRequest(int method, String url, String data, Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener) {
        super(method, url, null == data ? "" : data, listener, errorListener);
        this.listener = listener;
        this.requestName = requestName;
    }

    /**
     * json data
     *
     * @param method
     * @param url
     * @param data
     * @param listener
     * @param errorListener
     */
    CustomJsonObjectRequest(int method, String url, JSONObject data, Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener) {
        super(method, url, null == data ? "" : data.toString(), listener, errorListener);
        this.listener = listener;
        this.requestName = requestName;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "UTF-8"));
            JSONObject jsonObject = new JSONObject(jsonString);
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(JSONObject response) {
        if (null != listener) listener.onResponse(response);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return header;
    }


    CustomJsonObjectRequest setCookie(String cookie) {
        header.put("Cookie", cookie);
        return this;
    }
}
