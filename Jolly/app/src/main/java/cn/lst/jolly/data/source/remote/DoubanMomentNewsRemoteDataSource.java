package cn.lst.jolly.data.source.remote;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.DoubanMomentNews;
import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.source.datasource.DoubanMomentNewsDataSource;
import cn.lst.jolly.retrofit.RetrofitApi;
import cn.lst.jolly.util.DateFormatUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lisongting on 2017/12/19.
 */

public class DoubanMomentNewsRemoteDataSource implements DoubanMomentNewsDataSource {

    private static DoubanMomentNewsRemoteDataSource INSTANCE = null;
    private Retrofit retrofit;
    private RetrofitApi.DoubanMomentService api;

    private DoubanMomentNewsRemoteDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.DOUBAN_MOMENT_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RetrofitApi.DoubanMomentService.class);
    }

    public static DoubanMomentNewsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoubanMomentNewsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getDoubanMomentNews(boolean forceUpdate, boolean clearCache, long date, @NonNull final LoadDoubanMomentDailyCallback callback) {
        api.getDoubanList(DateFormatUtil.formatDoubanMomentDateLongToString(date))
                .enqueue(new Callback<DoubanMomentNews>() {
                    @Override
                    public void onResponse(Call<DoubanMomentNews> call, Response<DoubanMomentNews> response) {
                        callback.onNewsLoaded(response.body().getPosts());
                    }

                    @Override
                    public void onFailure(Call<DoubanMomentNews> call, Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getFavorites(@NonNull LoadDoubanMomentDailyCallback callback) {

    }

    @Override
    public void getItem(int id, @NonNull GetNewsItemCallback callback) {

    }

    @Override
    public void favoriteItem(int itemId, boolean favorite) {

    }

    @Override
    public void saveAll(@NonNull List<DoubanMomentNewsPosts> list) {

    }
}
