package com.lst.wanandroid;

import android.app.Application;

import com.lst.wanandroid.core.dao.DaoSession;

public class WanAndroidApp extends Application {
    private static WanAndroidApp instance;

    private DaoSession mDaoSession;
    static {

    }

    public static synchronized WanAndroidApp getInstance(){
        return instance;
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
