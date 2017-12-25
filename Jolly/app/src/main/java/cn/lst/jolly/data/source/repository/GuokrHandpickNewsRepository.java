/*
 * Copyright 2016 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.lst.jolly.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marktony.zhihudaily.data.GuokrHandpickNewsResult;
import com.marktony.zhihudaily.data.source.datasource.GuokrHandpickDataSource;
import com.marktony.zhihudaily.util.DateFormatUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Concrete implementation to load {@link GuokrHandpickNewsResult} from the data sources into a cache.
 * <p>
 *     Use the remote data source firstly, which is obtained from the server.
 *     If the remote data was not available, then use the local data source,
 *     which was from the locally persisted in database.
 */

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
    public void getGuokrHandpickNews(boolean forceUpdate, boolean clearCache, int offset, int limit, @NonNull LoadGuokrHandpickNewsCallback callback) {

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
    public void getFavorites(@NonNull LoadGuokrHandpickNewsCallback callback) {
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
    public void getItem(int itemId, @NonNull GetNewsItemCallback callback) {
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
