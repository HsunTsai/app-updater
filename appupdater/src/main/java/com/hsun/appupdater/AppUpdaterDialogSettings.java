package com.hsun.appupdater;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class AppUpdaterDialogSettings {

    private boolean showDownload = true;
    private String dialogThemeColor = "#4DB6AC";
    private int
            updateInfoTextResource = R.string.dialog_header,
            updateBtnTextResource = R.string.common_update,
            downloadBtnTextResource = R.string.common_download;
    private View customHeaderView;


    AppUpdaterDialogSettings parse(String settings) {
        try {
            JSONObject settingJson = new JSONObject(settings);
            setUpdateBtnTextResource(DataVerify.getIntValue(settingJson, "updateBtnTextResource", updateBtnTextResource));
            setUpdateInfoTextResource(DataVerify.getIntValue(settingJson, "updateInfoTextResource", updateInfoTextResource));
            setDownloadBtnTextResource(DataVerify.getIntValue(settingJson, "downloadBtnTextResource", downloadBtnTextResource));
            setShowDownload(DataVerify.getBooleanValue(settingJson, "showDownload", showDownload));
            setDialogThemeColor(DataVerify.getStringValue(settingJson, "dialogThemeColor", dialogThemeColor));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    String toJson() {
        JSONObject settingJson = new JSONObject();
        try {
            settingJson.put("updateInfoTextResource", updateInfoTextResource);
            settingJson.put("updateBtnTextResource", updateBtnTextResource);
            settingJson.put("downloadBtnTextResource", downloadBtnTextResource);
            settingJson.put("showDownload", showDownload);
            settingJson.put("dialogThemeColor", dialogThemeColor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return settingJson.toString();
    }

    int getUpdateInfoTextResource() {
        return updateInfoTextResource;
    }

    /**
     * if string include ${version}, it will replace to your new release version
     * @param updateInfoTextResource
     * @return
     */
    public AppUpdaterDialogSettings setUpdateInfoTextResource(int updateInfoTextResource) {
        this.updateInfoTextResource = updateInfoTextResource;
        return this;
    }

    int getUpdateBtnTextResource() {
        return updateBtnTextResource;
    }

    /**
     * dialog "Update" button text
     * @param updateBtnTextResource
     * @return
     */
    public AppUpdaterDialogSettings setUpdateBtnTextResource(int updateBtnTextResource) {
        this.updateBtnTextResource = updateBtnTextResource;
        return this;
    }

    int getDownloadBtnTextResource() {
        return downloadBtnTextResource;
    }

    /**
     * dialog download app on browser button text
     * @param downloadBtnTextResource
     * @return
     */
    public AppUpdaterDialogSettings setDownloadBtnTextResource(int downloadBtnTextResource) {
        this.downloadBtnTextResource = downloadBtnTextResource;
        return this;
    }

    boolean isShowDownload() {
        return showDownload;
    }

    /**
     * show download(browser) button after upgrade error
     * @param showDownload
     * @return
     */
    public AppUpdaterDialogSettings setShowDownload(boolean showDownload) {
        this.showDownload = showDownload;
        return this;
    }

    View getCustomHeaderView() {
        return customHeaderView;
    }

    /**
     * your can set custom header view on Dialog
     * like `getLayoutInflater().inflate(R.layout.custom_updater_dialog_header, null)`
     * @param customHeaderView
     * @return
     */
    public AppUpdaterDialogSettings setCustomHeaderView(View customHeaderView) {
        this.customHeaderView = customHeaderView;
        return this;
    }

    String getDialogThemeColor() {
        return dialogThemeColor;
    }

    /**
     * your can custom dialog theme by '#XXXXXX'
     * @param dialogThemeColor
     * @return
     */
    public AppUpdaterDialogSettings setDialogThemeColor(String dialogThemeColor) {
        this.dialogThemeColor = dialogThemeColor;
        return this;
    }
}
