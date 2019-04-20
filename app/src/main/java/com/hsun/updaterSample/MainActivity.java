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
                                .setDownloadTextResource(R.string.hint_open_browser)
                                .setUpdateTextResource(R.string.common_update)
                                .setDialogThemeColor("#0087dc")
                                .setHeaderLayoutResource(R.layout.updater);
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
