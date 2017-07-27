package com.paperfish.espresso.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lisongting on 2017/7/15.
 */

/**
 * 这种单例模式很好
 * 线程安全：由于内部类的创建是由JVM完成的，该线程本身就是线程安全的
 * 懒加载：内部类ClientHolder中的retrofit并不会马上加载，而是要等到调用getInstance()的时候才会去加载
 *
 */

public class RetrofitClient {
    private RetrofitClient() {

    }

    private static class ClientHolder{
        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return ClientHolder.retrofit;
    }
}
