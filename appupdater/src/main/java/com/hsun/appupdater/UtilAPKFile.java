package com.hsun.appupdater;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Power by https://stackoverflow.com/questions/11575943/parse-file-name-from-url-before-downloading-the-file
 */

public class UtilAPKFile {
    static String getFullNameFromURL(String url, String version, int versionCode) {
        String initName = "latestApp_" + version + "_" + versionCode + ".apk";
        if (url == null) {
            return initName;
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return initName;
            }
        } catch (MalformedURLException e) {
            return initName;
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        initName = url.substring(startIndex, endIndex);
        if (initName.contains(version) && initName.contains(String.valueOf(versionCode))) {
            return initName;
        } else if (initName.contains(".")) {
            initName = initName.substring(0, initName.indexOf(".")) + "_" + version + "_" + versionCode + initName.substring(initName.indexOf("."));
        } else {
            initName = version + "_" + versionCode + ".apk";
        }
        return initName;
    }
}
