package com.hsun.updaterSample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hsun.appupdater.AppUpdater;
import com.hsun.appupdater.AppUpdaterDialogSettings;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.textView))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppUpdaterDialogSettings appUpdaterDialogSettings = new AppUpdaterDialogSettings();
                        appUpdaterDialogSettings
                                .setUpdateInfoTextResource(R.string.update_info_text) //setUpdateInfoTextResource will replact ${version} to your version
                                .setDownloadBtnTextResource(R.string.hint_open_browser)
                                .setUpdateBtnTextResource(R.string.common_update)
                                .setDialogThemeColor("#0087DC");
                                //.setCustomHeaderView(getLayoutInflater().inflate(R.layout.custom_updater_dialog_header, null));
                        new AppUpdater(MainActivity.this)
                                .setUpdateParam("https://www.hsunserver.ga/download/updateData.json")
//                                .setUpdateParam("https://www.hsunserver.ga/download/updateData.json", AppUpdater.RequestMethod.GET)
//                                .setUpdateParam("https://www.hsunserver.ga/download/updateData.json",
//                                        AppUpdater.RequestMethod.POST, data)
                                .setVersion(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
                                .setLogShow(true)
                                .setDialogSettings(appUpdaterDialogSettings)
                                .run();
                    }
                });
    }
}
