package com.hsun.appupdater;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUpdateData {
    public static UpdateDataModel parse(String updateData) {
        UpdateDataModel updateDataModel = new UpdateDataModel();
        try {
            JSONObject jsonData = new JSONObject(updateData);
            updateDataModel.setVersion(DataVerify.getStringValue(jsonData, "version", updateDataModel.getVersion()));
            updateDataModel.setBuildCode(DataVerify.getIntValue(jsonData, "buildCode", updateDataModel.getBuildCode()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return updateDataModel;
    }
}
