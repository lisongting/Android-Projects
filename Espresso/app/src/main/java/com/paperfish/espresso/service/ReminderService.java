package com.paperfish.espresso.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by lisongting on 2017/8/11.
 */

public class ReminderService extends IntentService {

    private SharedPreferences preferences;

    private CompositeDisposable compositeDisposable ;

    public static final String TAG = ReminderService.class.getSimpleName();

    public ReminderService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        compositeDisposable = new CompositeDisposable();
        log("onCreate()");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        log("onHandleIntent");


    }

    private void log(String string) {
        Log.i(TAG, TAG + " -- " + string);
    }
}
