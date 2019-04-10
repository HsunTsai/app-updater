package com.hsun.appupdater;

import android.app.Activity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AppUpdater {

    private Activity activity;
    private String updateURL, currentVersion;
    private AppUpdaterDialogSettings appUpdaterDialogSettings;
    private int currentVersionCode = -1;

    /*Request Settings*/
    private int requestMethod = Request.Method.GET;
    private JSONObject requestBodyJson;
    private DefaultRetryPolicy requestRetryPolicy;
    private String requestBodyStr, cookie;
    private Response.Listener<JSONObject> responseListener;

    public enum RequestMethod {
        GET,
        POST
    }

    public AppUpdater(final Activity activity) {

        this.activity = activity;
        this.requestRetryPolicy = new DefaultRetryPolicy(60 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        this.responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                final UpdateDataModel updateDataModel = JsonUpdateData.parse(response);
                if (updateDataModel.getVersionCode() > currentVersionCode ||
                        new Version(updateDataModel.getVersion()).compareTo(new Version(currentVersion.trim())) > 0) {
                    AppUpdaterDialog.instance(activity, response.toString(), appUpdaterDialogSettings)
                            .show();
                }
            }
        };
    }

    public AppUpdater setDialogSettings(AppUpdaterDialogSettings appUpdaterDialogSettings) {
        this.appUpdaterDialogSettings = appUpdaterDialogSettings;
        return this;
    }

    public AppUpdater setUpdateURL(String updateURL) {
        this.updateURL = updateURL;
        return this;
    }

    public AppUpdater setRequestMethod(RequestMethod requestMethod) {
        switch (requestMethod) {
            case GET:
                this.requestMethod = Request.Method.GET;
                break;
            case POST:
                this.requestMethod = Request.Method.POST;
                break;
        }
        return this;
    }

    public AppUpdater setRequestBody(JSONObject requestBody) {
        this.requestBodyJson = requestBody;
        return this;
    }

    public AppUpdater setRequestBody(String requestBodyStr) {
        this.requestBodyStr = requestBodyStr;
        return this;
    }

    public AppUpdater setRequestRetryPolicy(DefaultRetryPolicy requestRetryPolicy) {
        this.requestRetryPolicy = requestRetryPolicy;
        return this;
    }

    public AppUpdater setRequestCookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public AppUpdater setCurrentVersion(String version) {
        this.currentVersion = version;
        return this;
    }

    public AppUpdater setCurrentVersionCode(int versionCode) {
        this.currentVersionCode = versionCode;
        return this;
    }

    public void run() {
        if (currentVersion.isEmpty() && currentVersionCode == -1) {
            Log.e("appUpdater", "currentVersion or currentVersionCode should be set at least one");
        } else if (updateURL.isEmpty()) {
            Log.e("appUpdater", "updateURL should be set");
        } else {
            getUpdateData();
        }
    }

    private void getUpdateData() {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        CustomJsonObjectRequest jsonObjectRequest;
        if (null != requestBodyJson) {
            jsonObjectRequest = new CustomJsonObjectRequest(requestMethod, updateURL, requestBodyJson,
                    responseListener, null);
        } else if (null != requestBodyStr) {
            jsonObjectRequest = new CustomJsonObjectRequest(requestMethod, updateURL, requestBodyStr,
                    responseListener, null);
        } else {
            jsonObjectRequest = new CustomJsonObjectRequest(requestMethod, updateURL, "",
                    responseListener, null);
        }
        jsonObjectRequest.setRetryPolicy(requestRetryPolicy);
        jsonObjectRequest.setShouldCache(false);
        if (null != cookie) jsonObjectRequest.setCookie(cookie);
        requestQueue.add(jsonObjectRequest);
    }
}
