package com.lst.wanandroid.presenter.mainpager;

import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.mainpager.MainPagerContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.BaseResponse;
import com.lst.wanandroid.core.bean.main.banner.BannerData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.bean.main.login.LoginData;
import com.lst.wanandroid.core.event.CollectEvent;
import com.lst.wanandroid.core.event.LoginEvent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MainPagerPresenter extends BasePresenter<MainPagerContract.View>
                implements MainPagerContract.Presenter{
    private DataManager mDataManager;
    private boolean isRefresh = true;
    private int mCurrentPage;

    @Inject
    MainPagerPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(MainPagerContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent(){
        RxBus rxBus = RxBus.getDefault();
        Flowable<CollectEvent> flowable =
                rxBus.toFlowable(CollectEvent.class);


        addSubscribe(flowable.filter(new Predicate<CollectEvent>() {
            @Override
            public boolean test(CollectEvent collectEvent) throws Exception {
                return !collectEvent.isCancelCollectSuccess();
            }
        }).subscribe(new Consumer<CollectEvent>() {
            @Override
            public void accept(CollectEvent collectEvent) throws Exception {
                //收藏成功
                mView.showCollectSuccess();
            }
        }));

        addSubscribe(flowable.filter(new Predicate<CollectEvent>() {
            @Override
            public boolean test(CollectEvent collectEvent) throws Exception {
                return collectEvent.isCancelCollectSuccess();
            }
        }).subscribe(new Consumer<CollectEvent>() {
            @Override
            public void accept(CollectEvent collectEvent) throws Exception {
                //取消收藏成功
                mView.showCancelCollectSuccess();
            }
        }));

        addSubscribe(rxBus.toFlowable(LoginEvent.class)
                .filter(new Predicate<LoginEvent>() {
                    @Override
                    public boolean test(LoginEvent loginEvent) throws Exception {
                        return loginEvent.isLogin();
                    }
                }).subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        mView.showLoginView();
                    }
                }));
        addSubscribe(rxBus.toFlowable(LoginEvent.class)
                .filter(new Predicate<LoginEvent>() {
                    @Override
                    public boolean test(LoginEvent loginEvent) throws Exception {
                        return !loginEvent.isLogin();
                    }
                }).subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        mView.showLogoutView();
                    }
                }));
    }

    @Override
    public String getLoginAccount(){
        return mDataManager.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return mDataManager.getLoginPassword();
    }

    @Override
    public void loadMainPagerData() {
        String account = mDataManager.getLoginAccount();
        String password = mDataManager.getLoginPassword();
        //获取登录响应
        Observable<BaseResponse<LoginData>> loginObservable =mDataManager.getLoginData(account, password);
        //获取轮播图数据
        Observable<BaseResponse<List<BannerData>>> bannerObservable = mDataManager.getBannerData();

        Observable<BaseResponse<FeedArticleListData>> articleObservable = mDataManager.getFeedArticleList(0);
    }

    @Override
    public void getFeedArticleList(boolean isShowError) {

    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {

    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {

    }

    @Override
    public void getBannerData(boolean isShowError) {

    }

    @Override
    public void autoRefresh(boolean isShowError) {

    }

    @Override
    public void loadMore() {

    }
}
