package com.hsun.appupdater;

public class UpdateDataModel {

    private String version, apkUrl, updateInformation;
    private int versionCode = 0;
    private long releaseTime = 0, appSize = 0;
    private boolean constraint = false;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getUpdateInformation() {
        return updateInformation;
    }

    public void setUpdateInformation(String updateInformation) {
        this.updateInformation = updateInformation;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }
}
