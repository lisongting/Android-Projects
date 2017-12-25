
package cn.lst.jolly.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

import cn.lst.jolly.data.ZhihuDailyNewsQuestion;
import cn.lst.jolly.data.source.datasource.ZhihuDailyNewsDataSource;
import cn.lst.jolly.database.AppDatabase;
import cn.lst.jolly.database.DatabaseCreator;


public class ZhihuDailyNewsLocalDataSource implements ZhihuDailyNewsDataSource {

    @Nullable
    private static ZhihuDailyNewsLocalDataSource INSTANCE = null;

    @Nullable
    private AppDatabase mDb = null;

    private ZhihuDailyNewsLocalDataSource(@NonNull Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
    }

    public static ZhihuDailyNewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ZhihuDailyNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getZhihuDailyNews(boolean forceUpdate, boolean clearCache,final  long date, @NonNull final LoadZhihuDailyNewsCallback callback) {

        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<ZhihuDailyNewsQuestion>>() {

                @Override
                protected List<ZhihuDailyNewsQuestion> doInBackground(Void... voids) {
                    return mDb.zhihuDailyNewsDao().queryAllByDate(date);
                }

                @Override
                protected void onPostExecute(List<ZhihuDailyNewsQuestion> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoaded(list);
                    }
                }

            }.execute();
        }
    }

    @Override
    public void getFavorites(@NonNull final LoadZhihuDailyNewsCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if(mDb != null) {
            new AsyncTask<Void, Void, List<ZhihuDailyNewsQuestion>>() {

                @Override
                protected List<ZhihuDailyNewsQuestion> doInBackground(Void... voids) {
                    return mDb.zhihuDailyNewsDao().queryAllFavorites();
                }

                @Override
                protected void onPostExecute(List<ZhihuDailyNewsQuestion> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoaded(list);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void getItem(final int itemId, @NonNull final GetNewsItemCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, ZhihuDailyNewsQuestion>() {
                @Override
                protected ZhihuDailyNewsQuestion doInBackground(Void... voids) {
                    return mDb.zhihuDailyNewsDao().queryItemById(itemId);
                }

                @Override
                protected void onPostExecute(ZhihuDailyNewsQuestion item) {
                    super.onPostExecute(item);
                    if (item == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onItemLoaded(item);
                    }
                }
            }.execute();
        }
    }

    @Override
    public void favoriteItem(final int itemId,final boolean favorite) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ZhihuDailyNewsQuestion tmp = mDb.zhihuDailyNewsDao().queryItemById(itemId);
                    tmp.setFavorite(favorite);
                    mDb.zhihuDailyNewsDao().update(tmp);
                }
            }).start();
        }
    }

    @Override
    public void saveAll(@NonNull final List<ZhihuDailyNewsQuestion> list) {

        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mDb.beginTransaction();
                    try {
                        mDb.zhihuDailyNewsDao().insertAll(list);
                        mDb.setTransactionSuccessful();
                    } finally {
                        mDb.endTransaction();
                    }
                }
            }).start();
        }
    }
}
