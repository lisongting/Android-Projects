package com.paperfish.espresso.retrofit;

import com.paperfish.espresso.data.CompanyRecognition;
import com.paperfish.espresso.data.Package;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lisongting on 2017/7/15.
 * 用于描述网络请求的接口
 */

public interface RetrofitInterface {

    @GET(Api.COMPANY_QUERY)
    Observable<CompanyRecognition> query(@Query("text") String number);

    @GET(Api.PACKAGE_STATE)
    Observable<Package> getPackageState(@Query("type") String type, @Query("postid") String postId);
}
