package com.lst.wanandroid.core.http;

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
import com.lst.wanandroid.core.http.api.GeeksApis;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HttpHelperImpl implements HttpHelper {
    private GeeksApis geeksApis;

    @Inject
    HttpHelperImpl(GeeksApis geeksApis) {
        this.geeksApis = geeksApis;
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int pageNum) {
        return geeksApis.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getSearchList(int pageNum, String k) {
        return geeksApis.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<List<TopSearchData>>> getTopSearchData() {
        return geeksApis.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites() {
        return geeksApis.getUsefulSites();
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return geeksApis.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getKnowledgeHierarchyDetailData(int page, int cid) {
        return geeksApis.getKnowledgeHierarchyDetailData(page,cid);
    }

    @Override
    public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
        return geeksApis.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData() {
        return geeksApis.getProjectClassifyData();
    }

    @Override
    public Observable<BaseResponse<ProjectListData>> getProjectListData(int page, int cid) {
        return geeksApis.getProjectListData(page,cid);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
        return geeksApis.getLoginData(username,password);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getRegisterData(String username, String password, String rePassword) {
        return geeksApis.getRegisterData(username,password,rePassword);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id) {
        return geeksApis.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectOutsideArticle(String title, String author, String link) {
        return geeksApis.addCollectOutsideArticle(title,author,link);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getCollectList(int page) {
        return geeksApis.getCollectList(page);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id) {
        return geeksApis.cancelCollectPageArticle(id,-1);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id) {
        return geeksApis.cancelCollectArticle(id,-1);
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return geeksApis.getBannerData();
    }
}
