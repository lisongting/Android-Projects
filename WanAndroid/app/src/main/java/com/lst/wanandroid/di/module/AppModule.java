package com.lst.wanandroid.di.module;

import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.db.DbHelper;
import com.lst.wanandroid.core.db.GreenDaoHelper;
import com.lst.wanandroid.core.http.HttpHelper;
import com.lst.wanandroid.core.http.RetrofitHelper;
import com.lst.wanandroid.core.prefs.PreferenceHelper;
import com.lst.wanandroid.core.prefs.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final WanAndroidApp application;

    public AppModule(WanAndroidApp app) {
        application = app;
    }

    @Provides
    @Singleton
    WanAndroidApp provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper){
        return retrofitHelper;
    }

    @Singleton
    @Provides
    DbHelper provideDBHelper(GreenDaoHelper helper) {
        return helper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferenceHelperImpl preferenceHelper) {
        return preferenceHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        return new DataManager(httpHelper, dbHelper, preferenceHelper);
    }
}
