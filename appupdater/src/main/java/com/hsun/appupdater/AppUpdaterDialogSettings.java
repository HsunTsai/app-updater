package com.hsun.appupdater;

import org.json.JSONException;
import org.json.JSONObject;

public class AppUpdaterDialogSettings {

    private boolean showDownload = true;
    private String dialogThemeColor = "#4DB6AC";
    private int
            updateTextResource = R.string.common_update,
            downloadTextResource = R.string.common_download,
    headerLayoutResource = 0;


    public AppUpdaterDialogSettings parse(String settings) {
        try {
            JSONObject settingJson = new JSONObject(settings);
            setUpdateTextResource(DataVerify.getIntValue(settingJson, "updateTextResource", updateTextResource));
            setDownloadTextResource(DataVerify.getIntValue(settingJson, "downloadTextResource", downloadTextResource));
            setShowDownload(DataVerify.getBooleanValue(settingJson, "showDownload", showDownload));
            setHeaderLayoutResource(DataVerify.getIntValue(settingJson, "headerLayoutResource", headerLayoutResource));
            setDialogThemeColor(DataVerify.getStringValue(settingJson, "dialogThemeColor", dialogThemeColor));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String toJson() {
        JSONObject settingJson = new JSONObject();
        try {
            settingJson.put("updateTextResource", updateTextResource);
            settingJson.put("downloadTextResource", downloadTextResource);
            settingJson.put("showDownload", showDownload);
            settingJson.put("headerLayoutResource", headerLayoutResource);
            settingJson.put("dialogThemeColor", dialogThemeColor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return settingJson.toString();
    }

    public int getUpdateTextResource() {
        return updateTextResource;
    }

    public AppUpdaterDialogSettings setUpdateTextResource(int updateTextResource) {
        this.updateTextResource = updateTextResource;
        return this;
    }

    public int getDownloadTextResource() {
        return downloadTextResource;
    }

    public AppUpdaterDialogSettings setDownloadTextResource(int downloadTextResource) {
        this.downloadTextResource = downloadTextResource;
        return this;
    }

    public boolean isShowDownload() {
        return showDownload;
    }

    public AppUpdaterDialogSettings setShowDownload(boolean showDownload) {
        this.showDownload = showDownload;
        return this;
    }

    public int getHeaderLayoutResource() {
        return headerLayoutResource;
    }

    public AppUpdaterDialogSettings setHeaderLayoutResource(int headerLayoutResource) {
        this.headerLayoutResource = headerLayoutResource;
        return this;
    }

    public String getDialogThemeColor() {
        return dialogThemeColor;
    }

    public AppUpdaterDialogSettings setDialogThemeColor(String dialogThemeColor) {
        this.dialogThemeColor = dialogThemeColor;
        return this;
    }
}
