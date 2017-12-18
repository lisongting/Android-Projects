package cn.lst.jolly.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.source.datasource.DoubanMomentContentDataSource;
import cn.lst.jolly.database.AppDatabase;
import cn.lst.jolly.database.DatabaseCreator;

/**
 * Created by lisongting on 2017/12/18.
 */

public class DoubanMomentContentLocalDataSource implements DoubanMomentContentDataSource{
    private static DoubanMomentContentLocalDataSource INSTANCE = null;
    private AppDatabase mDb = null;

    private DoubanMomentContentLocalDataSource(@NonNull Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
        mDb = creator.getDatabase();

    }

    public static DoubanMomentContentLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentContentLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentContent(final int id, @NonNull final LoadDoubanMomentContentCallback callback) {
        if (mDb == null) {
            callback.onDataNotAvailable();
            return;
        }
        //todo :这里应该可以采用更好的实现
        new AsyncTask<Void,Void,DoubanMomentContent>(){
            @Override
            protected DoubanMomentContent doInBackground(Void... voids) {
                return mDb.doubanMomentContentDao().queryContentById(id);
            }

            @Override
            protected void onPostExecute(DoubanMomentContent content) {
                super.onPostExecute(content);
                if (content == null) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onContentLoaded(content);
                }
            }
        }.execute();

    }

    @Override
    public void saveContent(@NonNull final DoubanMomentContent content) {
        if (mDb != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mDb.beginTransaction();
                    try {
                        mDb.doubanMomentContentDao().insert(content);
                        mDb.setTransactionSuccessful();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        mDb.endTransaction();
                    }
                }
            }).start();

        }
    }
}
