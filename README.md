# App Updater

## Demo Image

| Check Version | Download APK | Install | Dwonload Error |
| ------------- | ------------- | ------------- | ------------- |
| <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo.gif" title="Live Demo" width="200" height="400" /></kbd>  | <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo_2.png" title="Download APK" width="200" height="400"/></kbd>  | <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo_3.png" title="Install" width="200" height="400"/></kbd>  | <kbd><img src="http://www.hsunapi.ga/images/AppUpdaterDemo_4.png" title="Dwonload Error" width="200" height="400"/></kbd>  |

## Setup

### (1) Gradle (This lib use dataBinding, Plz add dataBinding enabled = true)
```
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
```
Add permission
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

```
Add uses-library & provider in your <application>
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

## Pattern

Created by MVVM pattern


## Dependencies

[Volley](https://mvnrepository.com/artifact/eu.the4thfloor.volley/com.android.volley/2015.05.28)
