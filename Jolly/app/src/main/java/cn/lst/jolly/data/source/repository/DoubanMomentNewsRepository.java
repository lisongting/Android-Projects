package cn.lst.jolly.data.source.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.source.datasource.DoubanMomentNewsDataSource;
import cn.lst.jolly.util.DateFormatUtil;

/**
 * Created by lisongting on 2017/12/19.
 */

public class DoubanMomentNewsRepository implements DoubanMomentNewsDataSource {
    private static DoubanMomentNewsRepository INSTANCE = null;
    private DoubanMomentNewsDataSource mLocalDataSource;
    private DoubanMomentNewsDataSource mRemoteDataSource;
    private Map<Integer, DoubanMomentNewsPosts> mCachedItems;

    private DoubanMomentNewsRepository(DoubanMomentNewsDataSource mRemoteDataSource,
                                       DoubanMomentNewsDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static DoubanMomentNewsRepository getInstance(DoubanMomentNewsDataSource mRemoteDataSource,
                                                         DoubanMomentNewsDataSource mLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentNewsRepository(mRemoteDataSource, mLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentNews(boolean forceUpdate, final boolean clearCache, final long date, @NonNull final LoadDoubanMomentDailyCallback callback) {
        if (mCachedItems != null && !forceUpdate) {
            callback.onNewsLoaded(new ArrayList<DoubanMomentNewsPosts>(mCachedItems.values()));
            return;
        }
        //先从网络加载，加载不到再从本地缓存中加载
        mRemoteDataSource.getDoubanMomentNews(false, clearCache, date, new LoadDoubanMomentDailyCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> list) {
                refreshCache(clearCache, list);
                callback.onNewsLoaded(new ArrayList<>(mCachedItems.values()));
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.getDoubanMomentNews(false, clearCache, date, new LoadDoubanMomentDailyCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> list) {
                        refreshCache(clearCache, list);
                        callback.onNewsLoaded(new ArrayList<DoubanMomentNewsPosts>(mCachedItems.values()));
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
    public void getFavorites(@NonNull final LoadDoubanMomentDailyCallback callback) {
        mLocalDataSource.getFavorites(new LoadDoubanMomentDailyCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> list) {
                callback.onNewsLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getItem(final int id, @NonNull final GetNewsItemCallback callback) {
        final DoubanMomentNewsPosts cachedItem = getItemWithId(id);

        if (cachedItem != null) {
            callback.onItemLoaded(cachedItem);
            return;
        }
        mLocalDataSource.getItem(id, new GetNewsItemCallback() {
            @Override
            public void onItemLoaded(@NonNull DoubanMomentNewsPosts item) {
                if (mCachedItems == null) {
                    mCachedItems = new LinkedHashMap<>();
                }
                mCachedItems.put(item.getId(), item);
                callback.onItemLoaded(item);
            }

            @Override
            public void onDataNotAvailable() {
                mRemoteDataSource.getItem(id, new GetNewsItemCallback() {
                    @Override
                    public void onItemLoaded(@NonNull DoubanMomentNewsPosts item) {
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
        mRemoteDataSource.favoriteItem(itemId,favorite);
        mLocalDataSource.favoriteItem(itemId, favorite);

        DoubanMomentNewsPosts cachedItem = getItemWithId(itemId);
        if (cachedItem != null) {
            cachedItem.setFavorite(favorite);
        }
    }

    @Override
    public void saveAll(@NonNull List<DoubanMomentNewsPosts> list) {
        for (DoubanMomentNewsPosts item : list) {
            // Set the timestamp.
            item.setTimestamp(DateFormatUtil.formatDoubanMomentDateStringToLong(item.getPublishedTime()));
            mCachedItems.put(item.getId(), item);
        }

        mLocalDataSource.saveAll(list);
        mRemoteDataSource.saveAll(list);

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private  DoubanMomentNewsPosts getItemWithId(int id) {
        return (mCachedItems == null || mCachedItems.isEmpty() ? null : mCachedItems.get(id));
    }

    private void refreshCache(boolean clearCache, List<DoubanMomentNewsPosts> list) {
        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        if (clearCache) {
            mCachedItems.clear();
        }
        for (DoubanMomentNewsPosts item : list) {
            mCachedItems.put(item.getId(), item);
        }
    }
}
