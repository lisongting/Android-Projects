package cn.lst.jolly.data.source.datasource;

import android.support.annotation.NonNull;

import cn.lst.jolly.data.DoubanMomentContent;

/**
 * Created by lisongting on 2017/12/18.
 */

public interface DoubanMomentContentDataSource {
    interface LoadDoubanMomentContentCallback{
        void onContentLoaded(@NonNull DoubanMomentContent content);

        void onDataNotAvailable();
    }

    void getDoubanMomentContent(int id,@NonNull LoadDoubanMomentContentCallback callback);

    void saveContent(@NonNull DoubanMomentContent content);
}
