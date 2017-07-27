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

    //在BaseURL(http://www.kuaidi100.com/)的基础上拼接了autonumber/autoComNum ，再加上query中的参数"text"
    //实际得到的是：http://www.kuaidi100.com/autonumber/autoComNum?text=number
    @GET(Api.COMPANY_QUERY)
    Observable<CompanyRecognition> query(@Query("text") String number);

    //在BaseURL(http://www.kuaidi100.com/)的基础上拼接了query ，再加上"type"和"postid"参数
    //实际得到的是：http://www.kuaidi100.com/query?type=<type>&postid=<postId>
    @GET(Api.PACKAGE_STATE)
    Observable<Package> getPackageState(@Query("type") String type, @Query("postid") String postId);
}
