

package cn.lst.jolly.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import cn.lst.jolly.data.GuokrHandpickContent;
import cn.lst.jolly.data.GuokrHandpickContentResult;
import cn.lst.jolly.data.source.datasource.GuokrHandpickContentDataSource;
import cn.lst.jolly.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GuokrHandpickContentRemoteDataSource implements GuokrHandpickContentDataSource {

    @Nullable
    private static GuokrHandpickContentRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private GuokrHandpickContentRemoteDataSource() {}

    public static GuokrHandpickContentRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GuokrHandpickContentRemoteDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getGuokrHandpickContent(int id, @NonNull final LoadGuokrHandpickContentCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.GUOKR_HANDPICK_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi.GuokrHandpickService service = retrofit.create(RetrofitApi.GuokrHandpickService.class);

        service.getGuokrContent(id)
                .enqueue(new Callback<GuokrHandpickContent>() {
                    @Override
                    public void onResponse(Call<GuokrHandpickContent> call, Response<GuokrHandpickContent> response) {
                        callback.onContentLoaded(response.body().getResult());
                    }

                    @Override
                    public void onFailure(Call<GuokrHandpickContent> call, Throwable t) {

                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void saveContent(@NonNull GuokrHandpickContentResult content) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }
}
