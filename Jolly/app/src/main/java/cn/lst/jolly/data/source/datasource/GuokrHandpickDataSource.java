
package cn.lst.jolly.data.source.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.GuokrHandpickNewsResult;

public interface GuokrHandpickDataSource {

    interface LoadGuokrHandpickNewsCallback {

        void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> list);

        void onDataNotAvailable();

    }

    interface GetNewsItemCallback {

        void onItemLoaded(@NonNull GuokrHandpickNewsResult item);

        void onDataNotAvailable();

    }

    void getGuokrHandpickNews(boolean forceUpdate, boolean clearCache, int offset, int limit, @NonNull LoadGuokrHandpickNewsCallback callback);

    void getFavorites(@NonNull LoadGuokrHandpickNewsCallback callback);

    void getItem(int itemId, @NonNull GetNewsItemCallback callback);

    void favoriteItem(int itemId, boolean favorite);

    void saveAll(@NonNull List<GuokrHandpickNewsResult> list);

}
