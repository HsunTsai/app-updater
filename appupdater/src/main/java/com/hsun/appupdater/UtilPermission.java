package com.hsun.appupdater;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class UtilPermission {
    public static boolean getWriteExternalStorage(Activity activity) {
        //Android M以上才有讀取權限問題
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = activity.checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                //取得權限
                activity.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Config.EXTERNAL_STORAGE_PERMISSION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
