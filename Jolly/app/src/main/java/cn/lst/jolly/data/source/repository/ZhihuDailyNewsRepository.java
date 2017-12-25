
package cn.lst.jolly.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.lst.jolly.data.ZhihuDailyNewsQuestion;
import cn.lst.jolly.data.source.datasource.ZhihuDailyNewsDataSource;


public class ZhihuDailyNewsRepository implements ZhihuDailyNewsDataSource {

    @Nullable
    private static ZhihuDailyNewsRepository INSTANCE = null;

    @NonNull
    private final ZhihuDailyNewsDataSource mLocalDataSource;

    @NonNull
    private final ZhihuDailyNewsDataSource mRemoteDataSource;

    private Map<Integer, ZhihuDailyNewsQuestion> mCachedItems;

    // Prevent direct instantiation.
    private ZhihuDailyNewsRepository(@NonNull ZhihuDailyNewsDataSource remoteDataSource,
                                     @NonNull ZhihuDailyNewsDataSource localDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static ZhihuDailyNewsRepository getInstance(@NonNull ZhihuDailyNewsDataSource remoteDataSource,
                                                       @NonNull ZhihuDailyNewsDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ZhihuDailyNewsRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getZhihuDailyNews(boolean forceUpdate,final boolean clearCache, final long date, @NonNull final LoadZhihuDailyNewsCallback callback) {

        if (mCachedItems != null && !forceUpdate) {
            callback.onNewsLoaded(new ArrayList<>(mCachedItems.values()));
            return;
        }

        // Get data by accessing network first.
        mRemoteDataSource.getZhihuDailyNews(false, clearCache, date, new LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyNewsQuestion> list) {
                refreshCache(clearCache, list);
                callback.onNewsLoaded(new ArrayList<>(mCachedItems.values()));
                // Save these item to database.
                saveAll(list);
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.getZhihuDailyNews(false, false, date, new LoadZhihuDailyNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<ZhihuDailyNewsQuestion> list) {
                        refreshCache(clearCache, list);
                        callback.onNewsLoaded(new ArrayList<>(mCachedItems.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void getFavorites(@NonNull final LoadZhihuDailyNewsCallback callback) {
        mLocalDataSource.getFavorites(new LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyNewsQuestion> list) {
                callback.onNewsLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getItem(final int itemId, @NonNull final  GetNewsItemCallback callback) {
        ZhihuDailyNewsQuestion cachedItem = getItemWithId(itemId);

        if (cachedItem != null) {
            callback.onItemLoaded(cachedItem);
            return;
        }

        mLocalDataSource.getItem(itemId, new GetNewsItemCallback() {
            @Override
            public void onItemLoaded(@NonNull ZhihuDailyNewsQuestion item) {
                if (mCachedItems == null) {
                    mCachedItems = new LinkedHashMap<>();
                }
                mCachedItems.put(item.getId(), item);
                callback.onItemLoaded(item);
            }

            @Override
            public void onDataNotAvailable() {
                mRemoteDataSource.getItem(itemId, new GetNewsItemCallback() {
                    @Override
                    public void onItemLoaded(@NonNull ZhihuDailyNewsQuestion item) {
                        if (mCachedItems == null) {
                            mCachedItems = new LinkedHashMap<>();
                        }
                        mCachedItems.put(item.getId(), item);
                        callback.onItemLoaded(item);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void favoriteItem(int itemId, boolean favorite) {
        mRemoteDataSource.favoriteItem(itemId, favorite);
        mLocalDataSource.favoriteItem(itemId, favorite);

        ZhihuDailyNewsQuestion cachedItem = getItemWithId(itemId);
        if (cachedItem != null) {
            cachedItem.setFavorite(favorite);
        }
    }

    @Override
    public void saveAll(@NonNull List<ZhihuDailyNewsQuestion> list) {
        mLocalDataSource.saveAll(list);
        mRemoteDataSource.saveAll(list);

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }

        for (ZhihuDailyNewsQuestion item : list) {
            // Note:  Setting of timestamp was done in the {@link ZhihuDailyNewsRemoteDataSource} class.
            mCachedItems.put(item.getId(), item);
        }
    }

    private void refreshCache(boolean clearCache, List<ZhihuDailyNewsQuestion> list) {

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        if (clearCache) {
            mCachedItems.clear();
        }
        for (ZhihuDailyNewsQuestion item : list) {
            mCachedItems.put(item.getId(), item);
        }
    }

    @Nullable
    private ZhihuDailyNewsQuestion getItemWithId(int id) {
        return (mCachedItems == null || mCachedItems.isEmpty()) ? null : mCachedItems.get(id);
    }

}
