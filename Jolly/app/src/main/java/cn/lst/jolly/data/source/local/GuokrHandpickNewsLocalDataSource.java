package cn.lst.jolly.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.source.datasource.GuokrHandpickDataSource;
import cn.lst.jolly.database.AppDatabase;
import cn.lst.jolly.database.DatabaseCreator;

/**
 * Created by lisongting on 2017/12/25.
 */

public class GuokrHandpickNewsLocalDataSource implements GuokrHandpickDataSource {

    private static GuokrHandpickNewsLocalDataSource INSTANCE = null;
    private AppDatabase mDb = null;

    private GuokrHandpickNewsLocalDataSource(Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
        mDb = creator.getDatabase();
    }

    public static GuokrHandpickNewsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickNewsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getGuokrHandpickNews(boolean forceUpdate, boolean clearCache, final int offset, final int limit, @NonNull final LoadGuokrHandpickNewsCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }
        if (mDb != null) {
            new AsyncTask<Void, Void, List<GuokrHandpickNewsResult>>(){

                @Override
                protected List<GuokrHandpickNewsResult> doInBackground(Void... voids) {
                    return mDb.guokrHandpickNewsDao().queryAllByOffsetAndLimit(offset,limit);
                }

                protected void onPostExecute(List<GuokrHandpickNewsResult> list) {
                    super.onPostExecute(list);
                    if (list == null) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onNewsLoad(list);
                    }
                }
            }.execute();
        }

    }

    @Override
    public void getFavorites(@NonNull final LoadGuokrHandpickNewsCallback callback) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }
        if (mDb != null) {
            new AsyncTask<Void, Void, List<GuokrHandpickNewsResult>>() {

                protected void onPostExecute(List<GuokrHandpickNewsResult> list) {
                    super.onPostExecute(list);
                    if (list != null) {
                        callback.onNewsLoad(list);
                    } else {
                        callback.onDataNotAvailable();
                    }
                }

                @Override
                protected List<GuokrHandpickNewsResult> doInBackground(Void... voids) {
                    return mDb.guokrHandpickNewsDao().queryAllFavorite();
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
            new AsyncTask<Void, Void, GuokrHandpickNewsResult>() {

                @Override
                protected GuokrHandpickNewsResult doInBackground(Void... voids) {
                    return mDb.guokrHandpickNewsDao().queryItemById(itemId);
                }

                @Override
                protected void onPostExecute(GuokrHandpickNewsResult item) {
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
            new Thread(new Runnable(){
                public void run(){
                    GuokrHandpickNewsResult tmp = mDb.guokrHandpickNewsDao().queryItemById(itemId);
                    if (tmp != null) {
                        tmp.setFavorite(favorite);
                        mDb.guokrHandpickNewsDao().update(tmp);
                    }
                }
            }).start();
        }
    }

    @Override
    public void saveAll(@NonNull final List<GuokrHandpickNewsResult> list) {
        if (mDb == null) {
            mDb = DatabaseCreator.getInstance().getDatabase();
        }

        if (mDb != null) {
            new Thread(new Runnable(){
                public void run(){
                    mDb.beginTransaction();
                    try{
                        mDb.guokrHandpickNewsDao().insertAll(list);
                        mDb.setTransactionSuccessful();
                    }finally {
                        mDb.endTransaction();
                    }
                }
            }).start();
        }
    }
}
