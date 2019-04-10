package com.hsun.appupdater;

import org.json.JSONException;
import org.json.JSONObject;

class JsonUpdateData {

    static UpdateDataModel parse(String updateData) {
        try {
            JSONObject updateDataJson = new JSONObject(updateData);
            return parse(updateDataJson);
        } catch (JSONException e) {
            e.printStackTrace();
            return new UpdateDataModel();
        }
    }

    static UpdateDataModel parse(JSONObject updateData) {
        UpdateDataModel updateDataModel = new UpdateDataModel();

        updateDataModel.setVersion(DataVerify.getStringValue(updateData, "version", updateDataModel.getVersion()));
        updateDataModel.setVersionCode(DataVerify.getIntValue(updateData, "versionCode", updateDataModel.getVersionCode()));
        updateDataModel.setApkUrl(DataVerify.getStringValue(updateData, "apkUrl", updateDataModel.getApkUrl()));
        updateDataModel.setUpdateInformation(DataVerify.getStringValue(updateData, "updateInformation", updateDataModel.getUpdateInformation()));
        updateDataModel.setReleaseTime(DataVerify.getLongValue(updateData, "releaseTime", updateDataModel.getReleaseTime()));
        updateDataModel.setAppSize(DataVerify.getLongValue(updateData, "appSize", updateDataModel.getAppSize()));
        updateDataModel.setConstraint(DataVerify.getBooleanValue(updateData, "constraint", updateDataModel.isConstraint()));

        return updateDataModel;
    }
}
