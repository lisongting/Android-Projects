package cn.lst.jolly.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.GuokrHandpickContentResult;
import cn.lst.jolly.data.PostType;
import cn.lst.jolly.data.ZhihuDailyContent;
import cn.lst.jolly.database.AppDatabase;
import cn.lst.jolly.database.DatabaseCreator;
import cn.lst.jolly.retrofit.RetrofitApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lisongting on 2017/12/25.
 */

public class CacheService extends Service {

    public static final String FLAG_ID = "flag_id";
    public static final String FLAG_TYPE = "flag_type";

    public static final String BROADCAST_FILTER_ACTION = "cn.lst.jolly.LOCAL_BROADCAST";
    private static final int MSG_CLEAR_CACHE_DONE = 1;
    private AppDatabase mDb = null;
    private LocalReceiver mReceiver;
    private RetrofitApi.ZhihuDailyService mZhihuService;
    private RetrofitApi.DoubanMomentService mDoubanService;
    private RetrofitApi.GuokrHandpickService mGuokrService;

    private boolean mZhihuCacheDone = false;
    private boolean mDoubanCacheDone = false;
    private boolean mGuokrCacheDone = false;

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CLEAR_CACHE_DONE:
                    CacheService.this.stopSelf();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    public void onCreate(){
        super.onCreate();
        mReceiver = new LocalReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_FILTER_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,filter);
        mZhihuService = new Retrofit.Builder()
                .baseUrl(RetrofitApi.ZHIHU_DAILY_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitApi.ZhihuDailyService.class);
        mDoubanService = new Retrofit.Builder()
                .baseUrl(RetrofitApi.DOUBAN_MOMENT_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitApi.DoubanMomentService.class);
        mGuokrService = new Retrofit.Builder()
                .baseUrl(RetrofitApi.GUOKR_HANDPICK_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitApi.GuokrHandpickService.class);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        }
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(FLAG_ID, 0);
            //todo : ?
            @PostType
            int type = intent.getIntExtra(FLAG_TYPE, 0);
            switch (type) {
                case PostType.TYPE_ZHIHU:
                    cacheZhidhuDailyContent(id);
                    break;
                case PostType.TYPE_DOUBAN:
                    cacheDoubanContent(id);
                    break;
                case PostType.TYPE_GUOKR:
                    cacheGuokrContent(id);
                    break;
                default:
                    break;

            }
        }
    }

    private void cacheDoubanContent(final int id) {
        if (mDb == null) {
            DatabaseCreator creator = DatabaseCreator.getInstance();
            if (!creator.isDatabaseCreated()) {
                creator.createDb(this);
            }
            mDb = creator.getDatabase();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mDb != null && mDb.doubanMomentContentDao().queryContentById(id) == null) {
                    mDb.beginTransaction();
                    try {
                        // Call execute() rather than enqueue()
                        // or you will go back to main thread in onResponse() function.
                        DoubanMomentContent tmp = mDoubanService.getDoubanContent(id).execute().body();
                        if (tmp != null) {
                            mDb.doubanMomentContentDao().insert(tmp);
                            mDb.setTransactionSuccessful();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        mDb.endTransaction();
                        mDoubanCacheDone = true;
                        if (mZhihuCacheDone && mGuokrCacheDone) {
                            clearTimeoutContent();
                        }
                    }
                }
            }
        }).start();
    }

    private void cacheZhidhuDailyContent(final int id) {
        if (mDb == null) {
            DatabaseCreator creator = DatabaseCreator.getInstance();
            if (!creator.isDatabaseCreated()) {
                creator.createDb(this);
            }
            mDb = creator.getDatabase();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //先进行网络请求然后缓存到数据库
                if (mDb != null && mDb.zhihuDailyContentDao().queryContentById(id) == null) {
                    mDb.beginTransaction();
                    try {
                        ZhihuDailyContent tmp = mZhihuService.getZhihuContent(id).execute().body();
                        if (tmp != null) {
                            mDb.zhihuDailyContentDao().insert(tmp);
                            mDb.setTransactionSuccessful();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        mDb.endTransaction();
                        mZhihuCacheDone = true;
                        if (mDoubanCacheDone && mGuokrCacheDone) {
                            clearTimeoutContent();
                        }
                    }
                }
            }
        }).start();
    }



    private void cacheGuokrContent(final int id) {
        if (mDb == null) {
            DatabaseCreator creator = DatabaseCreator.getInstance();
            if (!creator.isDatabaseCreated()) {
                creator.createDb(this);
            }
            mDb = creator.getDatabase();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mDb != null && mDb.guokrHandpickContentDao().queryContentById(id) == null) {
                    mDb.beginTransaction();
                    try {
                        // Call execute() rather than enqueue()
                        // or you will go back to main thread in onResponse() function.
                        GuokrHandpickContentResult tmp = mGuokrService.getGuokrContent(id).execute().body().getResult();
                        if (tmp != null) {
                            mDb.guokrHandpickContentDao().insert(tmp);
                            mDb.setTransactionSuccessful();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        mDb.endTransaction();
                        mGuokrCacheDone = true;
                        if (mZhihuCacheDone && mDoubanCacheDone) {
                            clearTimeoutContent();
                        }
                    }
                }
            }
        }).start();
    }

    private void clearTimeoutContent() {
        if (mDb == null) {
            DatabaseCreator creator = DatabaseCreator.getInstance();
            if (!creator.isDatabaseCreated()) {
                creator.createDb(this);
            }
            mDb = creator.getDatabase();
        }
//        int dayCount
    }

    private int getDaysOfSavingArticles(int id) {
        switch (id) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 15;
            case 3:
                return 30;
            default:
                return 15;

        }
    }

}
