

package cn.lst.jolly.data.source.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.ZhihuDailyNewsQuestion;


public interface ZhihuDailyNewsDataSource {

    interface LoadZhihuDailyNewsCallback {

        void onNewsLoaded(@NonNull List<ZhihuDailyNewsQuestion> list);

        void onDataNotAvailable();

    }

    interface GetNewsItemCallback {

        void onItemLoaded(@NonNull ZhihuDailyNewsQuestion item);

        void onDataNotAvailable();

    }

    void getZhihuDailyNews(boolean forceUpdate, boolean clearCache, long date, @NonNull LoadZhihuDailyNewsCallback callback);

    void getFavorites(@NonNull LoadZhihuDailyNewsCallback callback);

    void getItem(int itemId, @NonNull GetNewsItemCallback callback);

    void favoriteItem(int itemId, boolean favorite);

    void saveAll(@NonNull List<ZhihuDailyNewsQuestion> list);

}
