package com.lst.wanandroid.presenter.mainpager;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.Constants;
import com.lst.wanandroid.app.WanAndroidApp;
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
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
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
        //获取主界面的文章列表
        Observable<BaseResponse<FeedArticleListData>> articleObservable = mDataManager.getFeedArticleList(0);
        Disposable disposable = Observable.zip(loginObservable, bannerObservable, articleObservable,
                (loginResponse,bannerResponse,feedArticleListResponse)->{
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(Constants.LOGIN_DATA,loginResponse);
                    map.put(Constants.BANNER_DATA,bannerResponse);
                    map.put(Constants.ARTICLE_DATA, feedArticleListResponse);
                    return map;
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<HashMap<String,Object>>(mView){
                    @Override
                    public void onNext(HashMap<String, Object> map) {
                        BaseResponse<LoginData> loginResponse = CommonUtil.cast(map.get(Constants.LOGIN_DATA));
                        if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            LoginData loginData = loginResponse.getData();
                            mDataManager.setLoginAccount(loginData.getUsername());
                            mDataManager.setLoginPassword(loginData.getPassword());
                            mDataManager.setLoginStatus(true);
                        } else {
                            mView.showAutoLoginFail();
                        }
                        BaseResponse<List<BannerData>> bannerResponse = CommonUtil.cast(map.get(Constants.BANNER_DATA));
                        if (bannerResponse != null) {
                            mView.showBannerDataList(bannerResponse.getData());
                        }
                        BaseResponse<FeedArticleListData> feedArticleListDataBaseResponse =
                                CommonUtil.cast(map.get(Constants.ARTICLE_DATA));
                        if (feedArticleListDataBaseResponse != null) {
                            mView.showArticleList(feedArticleListDataBaseResponse.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showAutoLoginFail();
                    }
                });

        addSubscribe(disposable);
    }

    @Override
    public void autoRefresh(boolean isShowError) {
        isRefresh = true;
        mCurrentPage = 0;
        getBannerData(isShowError);
        getFeedArticleList(isShowError);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        mCurrentPage ++;
        loadMoreData();
    }

    @Override
    public void getFeedArticleList(boolean isShowError) {
        Disposable disposable = mDataManager.getFeedArticleList(mCurrentPage)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .filter(new Predicate<FeedArticleListData>() {
                    @Override
                    public boolean test(FeedArticleListData feedArticleListData) throws Exception {
                        return mView!=null;
                    }
                }).subscribeWith(new BaseObserver<FeedArticleListData>(mView) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showArticleList(feedArticleListData,isRefresh);
                    }
                });
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreData() {
        addSubscribe(mDataManager.getFeedArticleList(mCurrentPage)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .filter(new Predicate<FeedArticleListData>() {
                @Override
                public boolean test(FeedArticleListData feedArticleListData) throws Exception {
                    return mView!=null;
                }
            })
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_article_list),
                    false) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    mView.showArticleList(feedArticleListData,isRefresh);
                }
            }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        //添加收藏
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleCollectResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    feedArticleData.setCollect(true);
                    mView.showCollectArticleData(position,feedArticleData,feedArticleListData);
                }
            }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleCollectResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    feedArticleData.setCollect(false);
                    mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
                }
            }));
    }

    @Override
    public void getBannerData(boolean isShowError) {
        addSubscribe(mDataManager.getBannerData()
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<List<BannerData>>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_banner_data),
                    isShowError) {
                @Override
                public void onNext(List<BannerData> bannerData) {
                    mView.showBannerDataList(bannerData);
                }
            }));
    }


}
