package com.lst.wanandroid.utils;

import com.lst.wanandroid.core.bean.BaseResponse;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.http.exception.OtherException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T,T> rxFlSchedulerHelper(){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    //统一线程处理
    public static <T>ObservableTransformer<T,T> rxSchedulerHelper(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    //统一返回结果处理：将BaseResponse<T>封装为Observable<T>类型
    public static <T> ObservableTransformer<BaseResponse<T>,T> handleResult(){
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        if (tBaseResponse.getErrorCode() == BaseResponse.SUCCESS &&
                                tBaseResponse.getData() != null &&
                                CommonUtil.isNetworkConnected()) {
                            return createData(tBaseResponse.getData());
                        } else {
                            return Observable.error(new OtherException());
                        }
                    }
                });
            }
        };
    }

    //收藏返回结果
    public static <T> ObservableTransformer<BaseResponse<T>,T> handleCollectResult(){
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        if (tBaseResponse.getErrorCode() == BaseResponse.SUCCESS
                                && CommonUtil.isNetworkConnected()) {
                            return createData(CommonUtil.cast(new FeedArticleListData()));
                            //todo:?
//                            return createData((new FeedArticleListData()));
                        } else {
                            return Observable.error(new OtherException());
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T t) {
//        return Observable.just(t);
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(t);
                    e.onComplete();
                } catch (Exception e1) {
                    e.onError(e1);
                }
            }
        });
    }
}
