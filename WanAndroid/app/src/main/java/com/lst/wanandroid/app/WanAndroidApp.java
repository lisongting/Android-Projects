package com.lst.wanandroid.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.lst.wanandroid.BuildConfig;
import com.lst.wanandroid.R;
import com.lst.wanandroid.core.dao.DaoMaster;
import com.lst.wanandroid.core.dao.DaoSession;
import com.lst.wanandroid.di.component.AppComponent;
import com.lst.wanandroid.di.module.AppModule;
import com.lst.wanandroid.di.module.HttpModule;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.log.TxtFormatStrategy;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class WanAndroidApp extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    private static WanAndroidApp instance;
    private RefWatcher refWatcher;
    private DaoSession mDaoSession;

    public static boolean isFirstRun;
    private static volatile AppComponent appComponent;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                return new DeliveryHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new BallPulseFooter(context).setAnimatingColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
        });
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

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build()
                .inject(this);
        instance = this;
        initGreenDao();

        initBugly();

        initLogger();

        //todo
        //判断是否在Analyzer进程里
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    private void initGreenDao(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    private void initLogger(){
        //DEBUG版本才打到控制台
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter(
                    PrettyFormatStrategy.newBuilder()
                            .tag(getString(R.string.app_name)).build()));
        }
        Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder()
                            .tag(getString(R.string.app_name))
                            .build(getPackageName(),getString(R.string.app_name))));
    }

    private void initBugly(){
        String packageName = getApplicationContext().getPackageName();
        Log.d(WanAndroidApp.class.getSimpleName(), "packageName:" + packageName);
        String processName = CommonUtil.getProcessName(android.os.Process.myPid());
        Log.d(WanAndroidApp.class.getSimpleName(), "processName:" + processName);
        //设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_ID, false, strategy);
    }

    public static synchronized AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector(){
        return mAndroidInjector;
    }
}
