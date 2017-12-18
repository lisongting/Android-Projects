package cn.lst.jolly.data.source.remote;

import android.support.annotation.NonNull;

import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.source.datasource.DoubanMomentContentDataSource;
import cn.lst.jolly.retrofit.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lisongting on 2017/12/18.
 * 远程数据源：通过网络获取数据
 */

public class DoubanMomentContentRemoteDataSource implements DoubanMomentContentDataSource{
    private static DoubanMomentContentRemoteDataSource INSTANCE = null;
    private Retrofit retrofit;
    private RetrofitApi.DoubanMomentService api;

    private DoubanMomentContentRemoteDataSource(){
        retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitApi.DOUBAN_MOMENT_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        api = retrofit.create(RetrofitApi.DoubanMomentService.class);
    }

    public static DoubanMomentContentRemoteDataSource getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentContentRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentContent(int id, @NonNull final LoadDoubanMomentContentCallback callback) {
        api.getDoubanMoment(id).enqueue(new Callback<DoubanMomentContent>() {
            @Override
            public void onResponse(Call<DoubanMomentContent> call, Response<DoubanMomentContent> response) {
                callback.onContentLoaded(response.body());
            }

            @Override
            public void onFailure(Call<DoubanMomentContent> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveContent(@NonNull DoubanMomentContent content) {

    }
}
