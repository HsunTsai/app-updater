package com.hsun.appupdater;

public class UpdateDataModel {

    private String version;
    private int buildCode = 0;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(int buildCode) {
        this.buildCode = buildCode;
    }
}
