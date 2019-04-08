package com.hsun.appupdater;

import org.json.JSONException;
import org.json.JSONObject;

public class DataVerify {

    public static String getStringValue(JSONObject data, String key, String defaultValue) {
        try {
            if (exist(data, key)) {
                return data.getString(key);
            } else {
                return defaultValue;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    static int getIntValue(JSONObject data, String key, int defaultValue) {
        try {
            if (exist(data, key)) {
                return data.getInt(key);
            } else {
                return defaultValue;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    static long getLongValue(JSONObject data, String key, long defaultValue) {
        try {
            if (exist(data, key)) {
                return data.getLong(key);
            } else {
                return defaultValue;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    static boolean getBooleanValue(JSONObject data, String key, boolean defaultValue) {
        try {
            if (exist(data, key)) {
                return data.getBoolean(key);
            } else {
                return defaultValue;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    static boolean exist(JSONObject data, String key) {
        try {
            if (null != data && null != key && data.has(key)) {
                return !"null".equals(data.getString(key));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
