package cn.lst.jolly.data.source.repository;

import android.support.annotation.NonNull;

import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.source.datasource.DoubanMomentContentDataSource;

/**
 * Created by lisongting on 2017/12/18.
 */

public class DoubanMomentContentRepository implements DoubanMomentContentDataSource{

    private static DoubanMomentContentRepository INSTANCE = null;

    //todo  思想: 依赖应该建立在接口上而不是具体的类
    private final DoubanMomentContentDataSource mLocalDataSource;
    private final DoubanMomentContentDataSource mRemoteDataSource;
    private DoubanMomentContent mContent;

    private DoubanMomentContentRepository(@NonNull DoubanMomentContentDataSource remoteDataSource,
                                          @NonNull DoubanMomentContentDataSource localDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static DoubanMomentContentRepository getInstance(DoubanMomentContentDataSource remoteDataSource,
                                                            DoubanMomentContentDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentContentRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    @Override
    public void getDoubanMomentContent(final int id, @NonNull final LoadDoubanMomentContentCallback callback) {
        if (mContent != null) {
            callback.onContentLoaded(mContent);
            return;
        }
        //先从网络中获取数据，如果网络中获取到，则显示从网络中获取的数据，如果网络中获取失败，则从本地数据库中加载
        mRemoteDataSource.getDoubanMomentContent(id, new LoadDoubanMomentContentCallback() {
            @Override
            public void onContentLoaded(@NonNull DoubanMomentContent content) {
                if (mContent == null) {
                    mContent = content;
                    saveContent(content);
                }
                callback.onContentLoaded(content);
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.getDoubanMomentContent(id, new LoadDoubanMomentContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull DoubanMomentContent content) {
                        if (mContent == null) {
                            mContent = content;
                            saveContent(content);
                        }
                        callback.onContentLoaded(content);
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
    public void saveContent(@NonNull DoubanMomentContent content) {
        //缓存到本地
        mLocalDataSource.saveContent(content);
//        mRemoteDataSource.saveContent(content);
    }
}
