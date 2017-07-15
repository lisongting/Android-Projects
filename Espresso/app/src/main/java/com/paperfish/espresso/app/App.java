package com.paperfish.espresso.app;

import android.app.Application;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.paperfish.espresso.util.SettingsUtil;

import io.realm.Realm;

/**
 * Created by lisongting on 2017/7/14.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Realm
        Realm.init(this);

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(SettingsUtil.KEY_NIGHT_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}
