package cn.lst.jolly.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.source.datasource.DoubanMomentNewsDataSource;
import cn.lst.jolly.database.AppDatabase;
import cn.lst.jolly.database.DatabaseCreator;

/**
 * Created by lisongting on 2017/12/18.
 */

public class DoubanMomentNewsLocalDataSource implements DoubanMomentNewsDataSource {
    private static DoubanMomentNewsLocalDataSource INSTANCE = null;
    private AppDatabase mDb = null;

    private DoubanMomentNewsLocalDataSource(Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
    }

    public static DoubanMomentNewsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentNews(boolean forceUpdate, boolean clearCache, final long date, @NonNull final LoadDoubanMomentDailyCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }
        if (mDb != null) {
            new AsyncTask<Void, Void, List<DoubanMomentNewsPosts>>() {

                @Override
                protected List<DoubanMomentNewsPosts> doInBackground(Void... voids) {
                    return mDb.doubanMomentNewsDao().queryAllByDate(date);
                }

                protected void onPostExecute(List<DoubanMomentNewsPosts> list) {
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
    public void getFavorites(@NonNull final LoadDoubanMomentDailyCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new AsyncTask<Void, Void, List<DoubanMomentNewsPosts>>() {

                @Override
                protected List<DoubanMomentNewsPosts> doInBackground(Void... voids) {
                    return mDb.doubanMomentNewsDao().queryAllFavorites();
                }

                @Override
                protected void onPostExecute(List<DoubanMomentNewsPosts> list) {
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
    public void getItem(final int id, @NonNull final GetNewsItemCallback callback) {
            if (mDb == null) {
                mDb = DatabaseCreator.getInstance().getDatabase();
            }

            if (mDb != null) {
                new AsyncTask<Void, Void, DoubanMomentNewsPosts>() {

                    @Override
                    protected DoubanMomentNewsPosts doInBackground(Void... voids) {
                        return mDb.doubanMomentNewsDao().queryItemById(id);
                    }

                    @Override
                    protected void onPostExecute(DoubanMomentNewsPosts item) {
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
                    DoubanMomentNewsPosts tmp = mDb.doubanMomentNewsDao().queryItemById(itemId);
                    tmp.setFavorite(favorite);
                    mDb.doubanMomentNewsDao().update(tmp);
                }
            }).start();
        }
    }

    @Override
    public void saveAll(@NonNull final List<DoubanMomentNewsPosts> list) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mDb.beginTransaction();
                    try {
                        mDb.doubanMomentNewsDao().insertAll(list);
                        mDb.setTransactionSuccessful();
                    } finally {
                        mDb.endTransaction();
                    }
                }
            }).start();
        }
    }
}
