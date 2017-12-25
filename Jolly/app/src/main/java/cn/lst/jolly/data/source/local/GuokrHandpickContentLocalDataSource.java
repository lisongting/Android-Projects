package cn.lst.jolly.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import cn.lst.jolly.data.GuokrHandpickContentResult;
import cn.lst.jolly.data.source.datasource.GuokrHandpickContentDataSource;
import cn.lst.jolly.database.AppDatabase;
import cn.lst.jolly.database.DatabaseCreator;

/**
 * Created by lisongting on 2017/12/25.
 */

public class GuokrHandpickContentLocalDataSource implements GuokrHandpickContentDataSource {

    private static GuokrHandpickContentLocalDataSource INSTANCE = null;

    private AppDatabase mDb = null;

    private GuokrHandpickContentLocalDataSource(Context context) {
        DatabaseCreator creator = DatabaseCreator.getInstance();
        if (!creator.isDatabaseCreated()) {
            creator.createDb(context);
        }
        mDb = creator.getDatabase();
    }

    public static GuokrHandpickContentLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickContentLocalDataSource(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getGuokrHandpickContent(final int id, @NonNull final LoadGuokrHandpickContentCallback callback) {
        if (mDb == null) {
            callback.onDataNotAvailable();
            return;
        }
        new AsyncTask<Void, Void, GuokrHandpickContentResult>() {

            @Override
            protected GuokrHandpickContentResult doInBackground(Void... voids) {
                return mDb.guokrHandpickContentDao().queryContentById(id);
            }

            protected void onPostExecute(GuokrHandpickContentResult contentResult) {
                super.onPostExecute(contentResult);
                if (contentResult != null) {
                    callback.onContentLoaded(contentResult);
                } else {
                    callback.onDataNotAvailable();
                }
            }

        }.execute();

    }

    @Override
    public void saveContent(@NonNull final GuokrHandpickContentResult content) {
        if (mDb != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mDb.beginTransaction();
                    try {
                        mDb.guokrHandpickContentDao().insert(content);
                        mDb.setTransactionSuccessful();
                    } finally{
                        mDb.endTransaction();
                    }

                }
            }).start();
        }
    }
}
