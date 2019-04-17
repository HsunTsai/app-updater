# Android App Updater

## Demo Image

| Check Version | Download APK | Install | Dwonload Error |
| ------------- | ------------- | ------------- | ------------- |
| <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo.gif" title="Live Demo" width="200" height="300" /></kbd>  | <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo_2.png" title="Download APK" width="200" height="300"/></kbd>  | <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo_3.png" title="Install" width="200" height="300"/></kbd>  | <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo_4.png" title="Dwonload Error" width="200" height="300"/></kbd>  |

## Test Pass
### Virtual
| Device | Adroid API | Type |
| ------------- | ------------- | ------------- |
| Nokia 7+ | 28 | Real |
| Samsung Galaxy S8 | 28 | Real |
| Samsung Galaxy S9+ | 26 | Real |
| Xperia XZ1 Compact | 26 | Real |
| Huawei Mate 9| 24 | Real |
| LG G6 | 24 | Real |
| Xiaomi Mix2 | 26 | Real |
| ASUS Zenfone 5 | 20 | Real |

### Real
| Device | Adroid API | Type |
| ------------- | ------------- | ------------- |
| Pixel 2 | 26 | Virtual |
| Pixel 2 | 28 | Virtual |
| Nexus 4 | 22 | Virtual |
| Nexus 5 | 19 | Virtual |
| Nexus 5X | 25 | Virtual 
| Nexus 6 | 24 | Virtual |
| Nexus 6P | 27 | Virtual |
| Nexus 7 (2012) | 22 | Virtual |
| Nexus 9 | 25 | Virtual |
| Nexus 10 | 22 | Virtual |

## Setup

### (1) Gradle (This lib use dataBinding, Plz add dataBinding enabled = true)
```groovy
android {
    ...
    repositories {
        maven { url 'https://jitpack.io' }
    }
    dataBinding {
        enabled = true
    }
    ...
}
dependencies {
    implementation 'com.github.HsunTsai:app-updater:1.0.1'
}
```


### (2) AndroidManiferst.xml

#### Add permission

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

#### Add uses-library & provider in your <application>

```xml
<uses-library
      android:name="org.apache.http.legacy"
      android:required="false" />
<provider
        android:name="com.hsun.appupdater.FileProvider"
        android:authorities="${applicationId}.fileProvider"
        android:grantUriPermissions="true"
        android:exported="false">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/update_apk_paths" />
</provider>
```

### (3) Your backend server

#### You need a backend server(API) to tell the version number that the application wants to upgrade.
#### The JSON format like => 
```json
{
    "version": "1.0.1",
    "versionCode": "2",
    "apkUrl": "https://www.hsunserver.ga/download/appUpdater_2_1.0.1.apk",
    "updateInformation": "●Your information 1\n●Your information 2\n●Your information 3",
    "releaseTime": 1551059568060,
    "appSize": 12806567,
    "constraint": true
}
```

### (4) Usage
```java
public void checkVersion(){
    AppUpdaterDialogSettings appUpdaterDialogSettings = new AppUpdaterDialogSettings();
    appUpdaterDialogSettings
        .setDownloadText("open browser to download APK")
        .setUpdateText("Upgrade");

    new AppUpdater(MainActivity.this)
        .setUpdateParam("https://www.hsunserver.ga/download/updateData.json")
        //.setUpdateParam("https://www.hsunserver.ga/download/updateData.json", AppUpdater.RequestMethod.GET)
        //.setUpdateParam("https://www.hsunserver.ga/download/updateData.json", AppUpdater.RequestMethod.POST, data)
        .setVersion(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        .setLogShow(true)
        .setDialogSettings(appUpdaterDialogSettings)
        .run();
}
```

## Pattern

Created by MVVM pattern


## Dependencies

[Volley](https://mvnrepository.com/artifact/eu.the4thfloor.volley/com.android.volley/2015.05.28)
