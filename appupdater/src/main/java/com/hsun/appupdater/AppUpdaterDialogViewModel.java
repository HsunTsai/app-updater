package com.hsun.appupdater;

import android.app.Activity;
import android.app.DownloadManager;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hsun.appupdater.databinding.AppUpdaterDialogBinding;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class AppUpdaterDialogViewModel extends ViewModel {

    private Activity activity;
    private AppUpdaterDialogBinding appUpdaterDialogBinding;
    private Listener listener;
    private UpdateDataModel updateDataModel;
    private String versionSPKey, appFileName;
    public final ObservableBoolean updateConstraint = new ObservableBoolean(false),
            btDownloadShow = new ObservableBoolean(true);
    public final ObservableField<DownloadState> downloadState = new ObservableField<>(DownloadState.UN_INITIAL);
    public final ObservableInt downloadProgress = new ObservableInt(0);
    public final ObservableField<String>
            updateTitle = new ObservableField<>(),
            updateInformation = new ObservableField<>(),
            updateFooter = new ObservableField<>(),
            btUpdateText = new ObservableField<>(),
            btDownloadText = new ObservableField<>();

    public enum DownloadState {
        UN_INITIAL,
        START,
        DOWNLOADING,
        FINISH,
        ERROR
    }

    AppUpdaterDialogViewModel(Activity activity, AppUpdaterDialogBinding appUpdaterDialogBinding) {
        this.activity = activity;
        this.appUpdaterDialogBinding = appUpdaterDialogBinding;
    }

    public interface Listener {
        public void onClose();
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    void setUpdateData(UpdateDataModel updateDataModel) {
        this.updateDataModel = updateDataModel;

        updateInformation.set(updateDataModel.getUpdateInformation());
        updateConstraint.set(updateDataModel.isConstraint());
        versionSPKey = updateDataModel.getVersion() + updateDataModel.getVersionCode();
        appFileName = UtilAPKFile.getFullNameFromURL(updateDataModel.getApkUrl(),
                updateDataModel.getVersion(), updateDataModel.getVersionCode());
    }

    void setAppUpdaterDialogSettings(AppUpdaterDialogSettings appUpdaterDialogSettings) {
        if (appUpdaterDialogSettings.getUpdateInfoTextResource() != 0)
            updateTitle.set(activity.getString(appUpdaterDialogSettings.getUpdateInfoTextResource()).replace("${version}", updateDataModel.getVersion()));
        if (appUpdaterDialogSettings.getUpdateBtnTextResource() != 0)
            btUpdateText.set(activity.getString(appUpdaterDialogSettings.getUpdateBtnTextResource()));
        if (appUpdaterDialogSettings.getDownloadBtnTextResource() != 0)
            btDownloadText.set(activity.getString(appUpdaterDialogSettings.getDownloadBtnTextResource()));
        btDownloadShow.set(appUpdaterDialogSettings.isShowDownload());
        setThemeColor(appUpdaterDialogSettings.getDialogThemeColor());
        if (null != appUpdaterDialogSettings.getCustomHeaderView()) {
            if (appUpdaterDialogSettings.getCustomHeaderView().getParent() != null) {
                ((ViewGroup) appUpdaterDialogSettings.getCustomHeaderView().getParent())
                        .removeView(appUpdaterDialogSettings.getCustomHeaderView());
            }
            appUpdaterDialogBinding.customHeader.removeView(appUpdaterDialogSettings.getCustomHeaderView());
            appUpdaterDialogBinding.customHeader.addView(appUpdaterDialogSettings.getCustomHeaderView());
            appUpdaterDialogBinding.headerContainer.defaultBgHeader.setVisibility(View.GONE);
        } else {
            appUpdaterDialogBinding.customHeader.setVisibility(View.GONE);
            appUpdaterDialogBinding.headerContainer.defaultBgHeader.setVisibility(View.VISIBLE);
        }
    }

    void setThemeColor(String themeColor) {
        int color = activity.getResources().getColor(R.color.colorPrimaryLight);
        try {
            color = Color.parseColor(themeColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setThemeColor(color);
    }

    void setThemeColor(int colorResource) {
        if (null != appUpdaterDialogBinding) {
            ((GradientDrawable) appUpdaterDialogBinding.headerContainer.bgLogo.getBackground()).setColor(colorResource);
            ((GradientDrawable) appUpdaterDialogBinding.headerContainer.bgHeader.getBackground()).setColor(colorResource);
            ((GradientDrawable) appUpdaterDialogBinding.imgUpdate.getBackground()).setColor(colorResource);
            LayerDrawable progressBarDrawable = (LayerDrawable) appUpdaterDialogBinding.progressBar.getProgressDrawable();
            progressBarDrawable.setColorFilter(colorResource, PorterDuff.Mode.SRC_IN);
        }
    }

    private void downloadRUN() {
        String downloadFilePath = activity.getExternalCacheDir() + "/" + appFileName;
        final File downloadFile = new File(downloadFilePath);
        if (downloadFile.exists()) {
            installAPK(downloadFile);
        } else {
            new DownloadFileTask()
                    .setListener(new DownloadFileTask.Listener() {
                        @Override
                        public void onStartDownload() {
                            downloadState.set(DownloadState.START);
                            UtilLog.show("download", "start");
                        }

                        @Override
                        public void onProgress(int progress) {
                            downloadProgress.set(progress);
                            downloadState.set(DownloadState.DOWNLOADING);
                            UtilLog.show("download_progress", String.valueOf(progress));
                        }

                        @Override
                        public void onFinishDownload() {
                            UtilLog.show("download", "finish");
                            downloadState.set(DownloadState.FINISH);
                            installAPK(downloadFile);
                        }

                        @Override
                        public void onError() {
                            downloadState.set(DownloadState.ERROR);
                            if (downloadFile.exists()) downloadFile.delete();
                            UtilLog.show("download", "failed");
                        }
                    })
                    .execute(updateDataModel.getApkUrl(), downloadFilePath);
        }
    }

    public void closeDialog(View view) {
        if (null != listener) listener.onClose();
    }

    public void closeNotRemind(View view) {
        if (null != Config.sharedPreferences && null != versionSPKey) {
            Config.sharedPreferences.edit().putBoolean(versionSPKey, true).apply();
        }
        if (null != listener) listener.onClose();
    }

    public void updateApp(View view) {
        downloadRUN();
    }

    public void downloadApp(View view) {
        if (!updateDataModel.isConstraint() && null != listener) listener.onClose();

        if (UtilPermission.getWriteExternalStorage(activity)) {
            UtilLog.show("download URL", updateDataModel.getApkUrl());
            UtilLog.show("download File Name", appFileName);
            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateDataModel.getApkUrl()));
            request.setMimeType("application/vnd.android.package-archive");
            request.setTitle(appFileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, appFileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);

            Toast.makeText(activity, activity.getString(R.string.common_start_download), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, activity.getString(R.string.alert_write_permission), Toast.LENGTH_LONG).show();
        }
    }

    private void installAPK(File file) {
        try {
            Intent intent = getInstallAppIntent(activity, file);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent getInstallAppIntent(Context context, File appFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", appFile);
//                activity.grantUriPermission(activity.getPackageName(),fileUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(appFile), "application/vnd.android.package-archive");
            }
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


//Old code

//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                        intent.setDataAndType(Uri.fromFile(new File(activity.getExternalCacheDir() + "/aaa.apk")), "application/vnd.android.package-archive");
//                        activity.startActivityForResult(intent, 99);
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        File apkFile = new File(activity.getExternalCacheDir() + "/aaa.apk");
//                        Uri contentUri = FileProvider.getUriForFile(activity, "com.hsun.updaterSample"+ ".fileProvider", apkFile);
//                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
////                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//                        activity.startActivityForResult(intent,99);
