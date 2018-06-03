package com.lst.wanandroid.di.component;

import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.di.module.AbstractAllActivityModule;
import com.lst.wanandroid.di.module.AbstractAllDialogFragmentModule;
import com.lst.wanandroid.di.module.AbstractAllFragmentModule;
import com.lst.wanandroid.di.module.AppModule;
import com.lst.wanandroid.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AbstractAllActivityModule.class,
        AbstractAllDialogFragmentModule.class,
        AbstractAllFragmentModule.class,
        AppModule.class,
        HttpModule.class})
public interface AppComponent {
    void inject(WanAndroidApp wanAndroidApp);

    WanAndroidApp getContext();

    DataManager getDataManager();
}
