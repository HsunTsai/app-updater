package com.hsun.appupdater;

import org.json.JSONException;
import org.json.JSONObject;

public class AppUpdaterDialogSettings {

    private String updateText, downloadText;
    private boolean showDownload;

    public AppUpdaterDialogSettings parse(String settings) {
        try {
            JSONObject settingJson = new JSONObject(settings);
            setUpdateText(DataVerify.getStringValue(settingJson, "updateText", updateText));
            setDownloadText(DataVerify.getStringValue(settingJson, "downloadText", downloadText));
            setShowDownload(DataVerify.getBooleanValue(settingJson, "showDownload", showDownload));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String toJson() {
        JSONObject settingJson = new JSONObject();
        try {
            settingJson.put("updateText", updateText);
            settingJson.put("downloadText", downloadText);
            settingJson.put("showDownload", showDownload);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return settingJson.toString();
    }

    public String getUpdateText() {
        return updateText;
    }

    public AppUpdaterDialogSettings setUpdateText(String updateText) {
        this.updateText = updateText;
        return this;
    }

    public String getDownloadText() {
        return downloadText;
    }

    public AppUpdaterDialogSettings setDownloadText(String downloadText) {
        this.downloadText = downloadText;
        return this;
    }

    public boolean isShowDownload() {
        return showDownload;
    }

    public AppUpdaterDialogSettings setShowDownload(boolean showDownload) {
        this.showDownload = showDownload;
        return this;
    }
}
