

package cn.lst.jolly.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.source.datasource.GuokrHandpickDataSource;
import cn.lst.jolly.util.DateFormatUtil;


public class GuokrHandpickNewsRepository implements GuokrHandpickDataSource {

    @Nullable
    private static GuokrHandpickNewsRepository INSTANCE = null;

    @NonNull
    private final GuokrHandpickDataSource mLocalDataSource;

    @NonNull
    private final GuokrHandpickDataSource mRemoteDataSource;

    private Map<Integer, GuokrHandpickNewsResult> mCachedItems;

    private GuokrHandpickNewsRepository(@NonNull GuokrHandpickDataSource remoteDataSource,
                                        @NonNull GuokrHandpickDataSource localDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static GuokrHandpickNewsRepository getInstance(@NonNull GuokrHandpickDataSource remoteDataSource,
                                                          @NonNull GuokrHandpickDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickNewsRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getGuokrHandpickNews(boolean forceUpdate, final boolean clearCache, final int offset, final int limit, final @NonNull LoadGuokrHandpickNewsCallback callback) {

        if (mCachedItems != null && !forceUpdate) {
            callback.onNewsLoad(new ArrayList<>(mCachedItems.values()));
            return;
        }

        mRemoteDataSource.getGuokrHandpickNews(false, clearCache, offset, limit, new LoadGuokrHandpickNewsCallback() {
            @Override
            public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> list) {
                refreshCache(clearCache, list);
                callback.onNewsLoad(new ArrayList<>(mCachedItems.values()));

                // Save whole list to database.
                saveAll(list);
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.getGuokrHandpickNews(false, clearCache, offset, limit, new LoadGuokrHandpickNewsCallback() {
                    @Override
                    public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> list) {
                        refreshCache(clearCache, list);
                        callback.onNewsLoad(new ArrayList<>(mCachedItems.values()));
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
    public void getFavorites(@NonNull final LoadGuokrHandpickNewsCallback callback) {
        mLocalDataSource.getFavorites(new LoadGuokrHandpickNewsCallback() {
            @Override
            public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> list) {
                callback.onNewsLoad(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getItem(final int itemId, @NonNull final GetNewsItemCallback callback) {
        GuokrHandpickNewsResult item = getItemWithId(itemId);

        if (item != null) {
            callback.onItemLoaded(item);
            return;
        }

        mLocalDataSource.getItem(itemId, new GetNewsItemCallback() {
            @Override
            public void onItemLoaded(@NonNull GuokrHandpickNewsResult item) {
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
                    public void onItemLoaded(@NonNull GuokrHandpickNewsResult item) {
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

        GuokrHandpickNewsResult cachedItem = getItemWithId(itemId);
        if (cachedItem != null) {
            cachedItem.setFavorite(favorite);
        }
    }

    @Override
    public void saveAll(@NonNull List<GuokrHandpickNewsResult> list) {
        for (GuokrHandpickNewsResult item : list) {
            // Set the timestamp.
            item.setTimestamp(DateFormatUtil.formatGuokrHandpickTimeStringToLong(item.getDatePublished()));
            mCachedItems.put(item.getId(), item);
        }

        mLocalDataSource.saveAll(list);
        mRemoteDataSource.saveAll(list);

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }

    }

    private void refreshCache(boolean clearCache, List<GuokrHandpickNewsResult> list) {
        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        if (clearCache) {
            mCachedItems.clear();
        }
        for (GuokrHandpickNewsResult item : list) {
            mCachedItems.put(item.getId(), item);
        }
    }

    @Nullable
    private GuokrHandpickNewsResult getItemWithId(int itemId) {
        return (mCachedItems == null || mCachedItems.isEmpty()) ? null : mCachedItems.get(itemId);
    }

}
