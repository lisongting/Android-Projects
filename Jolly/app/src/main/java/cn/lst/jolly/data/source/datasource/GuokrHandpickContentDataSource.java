
package cn.lst.jolly.data.source.datasource;

import android.support.annotation.NonNull;

import cn.lst.jolly.data.GuokrHandpickContentResult;


public interface GuokrHandpickContentDataSource {

    interface LoadGuokrHandpickContentCallback {

        void onContentLoaded(@NonNull GuokrHandpickContentResult content);

        void onDataNotAvailable();

    }

    void getGuokrHandpickContent(int id, @NonNull LoadGuokrHandpickContentCallback callback);

    void saveContent(@NonNull GuokrHandpickContentResult content);

}
