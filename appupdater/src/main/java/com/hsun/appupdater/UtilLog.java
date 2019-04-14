package com.hsun.appupdater;

import android.util.Log;

public class UtilLog {
    public static void show(String log_name, String log_message) {
        if ((Config.showLog) && null != log_name && null != log_message) {
            Log.e(log_name, log_message);
        }
    }
}
