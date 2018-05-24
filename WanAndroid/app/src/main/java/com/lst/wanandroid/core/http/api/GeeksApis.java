package com.lst.wanandroid.core.http.api;

import com.lst.wanandroid.core.bean.BaseResponse;
import com.lst.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lst.wanandroid.core.bean.main.banner.BannerData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.bean.main.login.LoginData;
import com.lst.wanandroid.core.bean.main.search.TopSearchData;
import com.lst.wanandroid.core.bean.main.search.UsefulSiteData;
import com.lst.wanandroid.core.bean.navigation.NavigationListData;
import com.lst.wanandroid.core.bean.project.ProjectClassifyData;
import com.lst.wanandroid.core.bean.project.ProjectListData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeeksApis {

    String HOST = "http://www.wanandroid.com/";

    //获取feed文章列表.num是页数
    @GET("article/list/{num}/json")
    Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(@Path("num") int num);

    //根据关键字k，搜索数据，page是页数
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListData>> getSearchList(@Path("page") int page, @Path("k") String k);

    //获取热搜
    @GET("hotkey/json")
    @Headers("Cache-Control: public, max-age=36000")
    Observable<BaseResponse<List<TopSearchData>>> getTopSearchData();

    @GET("friend/json/")
    Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites();

    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    //知识体系
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();

    //获取知识体系下的文章
    //cid 是第二级页面的id
    @GET("article/list/{page}/json")
    Observable<BaseResponse<FeedArticleListData>>
                getKnowledgeHierarchyDetailData(@Path("page") int page, @Query("cid") int cid);

    @GET("navi/json")
    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();

    //项目分类
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData();

    //项目类别数据
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectListData>>
                getProjectListData(@Path("page") int page, @Query("cid") int cid);

    //登录
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>>
                getLoginData(@Field("username") String username, @Field("password") String password);

    //注册
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>>
            getRegisterData(@Field("username") String username,
                            @Field("password") String password,
                            @Field("repassword")String repassword);

    //收藏站内的文章
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<FeedArticleListData>> addCollectArticle(@Path("id") int id);

    //收藏站外的文章
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListData>>
            addCollectOutsideArticle(@Field("title")String title,
                                     @Field("author")String author,
                                     @Field("link")String link);

    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<FeedArticleListData>> getCollectList(@Path("page") int page);

    //取消收藏站内文章
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListData>>
            cancelCollectPageArticle(@Path("id")int id,@Field("originId")int originId);

    //取消收藏页面站内文章
    @POST("lg/uncollec_originId/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<FeedArticleListData>>
            cancelCollectArticle(@Path("id")int id,@Field("originId")int originId);


}
