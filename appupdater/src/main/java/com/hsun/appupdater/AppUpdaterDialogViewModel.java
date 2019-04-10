package com.hsun.appupdater;

import android.app.Activity;
import android.app.DownloadManager;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;

public class AppUpdaterDialogViewModel extends ViewModel {

    private Activity activity;
    private Listener listener;
    private UpdateDataModel updateDataModel;
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
    }

    void setAppUpdaterDialogSettings(AppUpdaterDialogSettings appUpdaterDialogSettings) {
        btUpdateText.set(null == appUpdaterDialogSettings.getUpdateText() ?
                activity.getString(R.string.common_update) : appUpdaterDialogSettings.getUpdateText());
        btDownloadText.set(null == appUpdaterDialogSettings.getDownloadText() ?
                activity.getString(R.string.common_download) : appUpdaterDialogSettings.getDownloadText());
        btDownloadShow.set(appUpdaterDialogSettings.isShowDownload());
    }

    private void updateGo() {

    }

    public void closeDialog(View view) {
        if (null != listener) listener.onClose();
    }

    public void updateApp(View view) {
        updateGo();
    }

    public void downloadApp(View view) {
        if (updateDataModel.isConstraint()) {
            ///////updateGo();
        } else {
            if (null != listener) listener.onClose();
        }


        if (UtilPermission.getWriteExternalStorage(activity)) {
            String fileName = FileNameUtil.get(updateDataModel.getApkUrl());
            DownloadManager downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateDataModel.getApkUrl()));
            //request.addRequestHeader("Cookie", );
            request.setMimeType("application/vnd.android.package-archive");
            request.setTitle(fileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);
        } else {
            Toast.makeText(activity, activity.getString(R.string.alert_write_permission), Toast.LENGTH_LONG).show();
        }
    }
}
