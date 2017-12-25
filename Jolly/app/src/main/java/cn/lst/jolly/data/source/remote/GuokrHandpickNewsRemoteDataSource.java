
package cn.lst.jolly.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import cn.lst.jolly.data.GuokrHandpickNews;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.source.datasource.GuokrHandpickDataSource;
import cn.lst.jolly.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GuokrHandpickNewsRemoteDataSource implements GuokrHandpickDataSource {

    @Nullable
    private static GuokrHandpickNewsRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private GuokrHandpickNewsRemoteDataSource() {}

    public static GuokrHandpickNewsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickNewsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getGuokrHandpickNews(boolean forceUpdate, boolean clearCache, int offset, int limit,final @NonNull LoadGuokrHandpickNewsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.GUOKR_HANDPICK_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi.GuokrHandpickService service = retrofit.create(RetrofitApi.GuokrHandpickService.class);

        service.getGuokrHandpick(offset, limit)
                .enqueue(new Callback<GuokrHandpickNews>() {
                    @Override
                    public void onResponse(Call<GuokrHandpickNews> call, Response<GuokrHandpickNews> response) {
                        callback.onNewsLoad(response.body().getResult());
                    }

                    @Override
                    public void onFailure(Call<GuokrHandpickNews> call, Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getFavorites(@NonNull LoadGuokrHandpickNewsCallback callback) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void getItem(int itemId, @NonNull GetNewsItemCallback callback) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void favoriteItem(int itemId, boolean favorite) {
// Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void saveAll(@NonNull List<GuokrHandpickNewsResult> list) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

}
