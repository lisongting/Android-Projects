package com.lst.wanandroid.presenter.main;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.main.MainContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.event.AutoLoginEvent;
import com.lst.wanandroid.core.event.LoginEvent;
import com.lst.wanandroid.core.event.NightModeEvent;
import com.lst.wanandroid.core.event.SwitchNavigationEvent;
import com.lst.wanandroid.core.event.SwitchProjectEvent;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseSubscriber;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    private DataManager dataManager;

    @Inject
    MainPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent(){
        RxBus bus = RxBus.getDefault();
        addSubscribe(bus.toFlowable(NightModeEvent.class)
                .compose(RxUtils.rxFlSchedulerHelper())
                .map(new Function<NightModeEvent, Boolean>() {
                    @Override
                    public Boolean apply(NightModeEvent nightModeEvent) throws Exception {
                        return nightModeEvent.getNightMode();
                    }
                })
                .subscribeWith(new BaseSubscriber<Boolean>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_cast_mode)) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mView.useNightMode(aBoolean);
                    }
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        registerEvent();
                    }
                })
        );

        addSubscribe(bus.toFlowable(LoginEvent.class)
            .filter(LoginEvent::isLogin)
            .subscribe(new Consumer<LoginEvent>() {
                @Override
                public void accept(LoginEvent loginEvent) throws Exception {
                    mView.showLoginView();
                }
            }));

        addSubscribe(bus.toFlowable(LoginEvent.class)
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

        addSubscribe(bus.toFlowable(AutoLoginEvent.class)
            .subscribe(new Consumer<AutoLoginEvent>() {
                @Override
                public void accept(AutoLoginEvent autoLoginEvent) throws Exception {
                    mView.showLoginView();
                }
            }));

        addSubscribe(bus.toFlowable(SwitchProjectEvent.class)
            .subscribe(new Consumer<SwitchProjectEvent>() {
                @Override
                public void accept(SwitchProjectEvent switchProjectEvent) throws Exception {
                    mView.showSwitchProject();
                }
            }));

        addSubscribe(bus.toFlowable(SwitchNavigationEvent.class)
            .subscribe(new Consumer<SwitchNavigationEvent>() {
                @Override
                public void accept(SwitchNavigationEvent switchNavigationEvent) throws Exception {
                    mView.showSwitchNavigation();
                }
            }));
    }

    @Override
    public void setCurrentPage(int page) {
        dataManager.setCurrentPage(page);
    }

    @Override
    public void setNightModeState(boolean b) {
        dataManager.setNightModeState(b);
    }
}
