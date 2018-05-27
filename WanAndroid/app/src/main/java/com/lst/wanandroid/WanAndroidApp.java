package com.lst.wanandroid;

import android.app.Application;
import android.content.Context;

import com.lst.wanandroid.core.dao.DaoSession;
import com.squareup.leakcanary.RefWatcher;

public class WanAndroidApp extends Application {
    private static WanAndroidApp instance;
    private RefWatcher refWatcher;
    private DaoSession mDaoSession;


    static {

    }

    public static synchronized WanAndroidApp getInstance(){
        return instance;
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }

    public static RefWatcher getRefWatcher(Context context) {
        //todo:?
        WanAndroidApp app = (WanAndroidApp) context.getApplicationContext();
        return app.refWatcher;
    }

}
