package cn.lst.jolly.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lisongting on 2017/12/18.
 */

public class DatabaseCreator {

    private final String TAG = "DatabaseCreator";

    private static DatabaseCreator INSTANCE = null;
    private AppDatabase mDb;

    //引用atomicBoolean 来确保线程同步
    private final AtomicBoolean mInitializing = new AtomicBoolean(true);
    private final AtomicBoolean mIsDbCreated = new AtomicBoolean(false);

    private static final Object LOCK = new Object();

    public synchronized static DatabaseCreator getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseCreator();
                }
            }
        }
        return INSTANCE;
    }

    public void createDb(final Context context) {
        log("Create Database in Thread:" + Thread.currentThread().getName());
        if (!mInitializing.compareAndSet(true, false)) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                log("create database in Thread : "+Thread.currentThread().getName());
                Context ctx = context.getApplicationContext();
                mDb = Room.databaseBuilder(ctx, AppDatabase.class, AppDatabase.DATABASE_NAME).build();
                mIsDbCreated.set(true);
            }
        }).start();

    }

    public boolean isDatabaseCreated(){
        return mIsDbCreated.get();
    }

    public AppDatabase getDatabase() {
        return mDb;
    }

    private void log(String s) {
        Log.i(TAG, s);
    }

}

