package cn.lst.jolly.data.source.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.DoubanMomentNewsPosts;

/**
 * Created by lisongting on 2017/12/18.
 */

public interface DoubanMomentNewsDataSource {

    interface LoadDoubanMomentDailyCallback{

        void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> list);

        void onDataNotAvailable();
    }

    interface GetNewsItemCallback{
        void onItemLoaded(@NonNull DoubanMomentNewsPosts item);

        void onDataNotAvailable();
    }

    void getDoubanMomentNews(boolean forceUpdate, boolean clearCache, long date, @NonNull LoadDoubanMomentDailyCallback callback);

    void getFavorites(@NonNull LoadDoubanMomentDailyCallback callback);

    void getItem(int id, @NonNull GetNewsItemCallback callback);

    void favoriteItem(int itemId, boolean favorite);

    void saveAll(@NonNull List<DoubanMomentNewsPosts> list);
}
