package com.hsun.appupdater;

import android.app.Activity;
import android.app.DownloadManager;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class AppUpdaterDialogViewModel extends ViewModel {

    private Activity activity;
    private Listener listener;
    private UpdateDataModel updateDataModel;
    private String versionSPKey;
    public final ObservableBoolean updateConstraint = new ObservableBoolean(false),
            btDownloadShow = new ObservableBoolean(false);
    public final ObservableField<String>
            updateTitle = new ObservableField<>(),
            updateInformation = new ObservableField<>(),
            updateFooter = new ObservableField<>(),
            btUpdateText = new ObservableField<>(),
            btDownloadText = new ObservableField<>();

    AppUpdaterDialogViewModel(Activity activity) {
        this.activity = activity;
    }

    public interface Listener {
        public void onClose();
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    void setUpdateData(UpdateDataModel updateDataModel) {
        this.updateDataModel = updateDataModel;
        updateTitle.set(activity.getString(R.string.dialog_header).replace("${version}", updateDataModel.getVersion()));
        updateInformation.set(updateDataModel.getUpdateInformation());
        updateConstraint.set(updateDataModel.isConstraint());
        versionSPKey = updateDataModel.getVersion() + updateDataModel.getVersionCode();
    }

    void setAppUpdaterDialogSettings(AppUpdaterDialogSettings appUpdaterDialogSettings) {
        btUpdateText.set(null == appUpdaterDialogSettings.getUpdateText() ?
                activity.getString(R.string.common_update) : appUpdaterDialogSettings.getUpdateText());
        btDownloadText.set(null == appUpdaterDialogSettings.getDownloadText() ?
                activity.getString(R.string.common_download) : appUpdaterDialogSettings.getDownloadText());
        btDownloadShow.set(appUpdaterDialogSettings.isShowDownload());
    }

    private void downloadRUN() {
        new DownloadFileTask()
                .setListener(new DownloadFileTask.Listener() {
                    @Override
                    public void onStartDownload() {
                        Log.e("download", "開始下載");
                    }

                    @Override
                    public void onProgress(int progress) {
                        Log.e("download", String.valueOf(progress));
                    }

                    @Override
                    public void onFinishDownload() {
                        Log.e("download", "下載完成");
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
                        openFile(new File(activity.getExternalCacheDir() + "/aaa.apk"));
                    }

                    @Override
                    public void onError() {
                        Log.e("download", "下載失敗");
                    }
                })
                .execute(updateDataModel.getApkUrl(),
                        activity.getExternalCacheDir() + "/aaa.apk");
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
        if (updateDataModel.isConstraint()) {
            downloadRUN();
        } else {
            if (null != listener) listener.onClose();
        }
//
//        if (UtilPermission.getWriteExternalStorage(activity)) {
//            String fileName = FileNameUtil.get(updateDataModel.getApkUrl());
//            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateDataModel.getApkUrl()));
//            //request.addRequestHeader("Cookie", );
//            request.setMimeType("application/vnd.android.package-archive");
//            request.setTitle(fileName);
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            downloadManager.enqueue(request);
//        } else {
//            Toast.makeText(activity, activity.getString(R.string.alert_write_permission), Toast.LENGTH_LONG).show();
//        }
    }

//    private void openFile(File file) {
//        //判读版本是否在8.0以上
//        if (Build.VERSION.SDK_INT >= 26) {
//            //来判断应用是否有权限安装apk
//            boolean installAllowed = activity.getPackageManager().canRequestPackageInstalls();
//            if (installAllowed) {
//                install(file);
//            } else {
//                activity.startActivity(new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS));
////                UtilPermission.getWriteExternalStorage(activity);
//            }
//        } else {
//            install(file);
//        }
//    }

    private void openFile(File file){
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION，
                //URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。
                //这个flag只提供可能持久授权。
                //但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现z
                intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", appFile);
                activity.grantUriPermission(activity.getPackageName(),fileUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
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

//    private void install(File file) {
//        Log.e("開啟install",file.getAbsolutePath());
//
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(android.content.Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= 24) {
//            //provider authorities
//            Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName()+".fileProvider", file);
//            //Granting Temporary Permissions to a URI
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        } else {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        }
//        activity.startActivity(intent);
//    }
}
