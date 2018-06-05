package com.lst.wanandroid.component;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class RxBus {
    private final FlowableProcessor<Object> bus;

    private RxBus(){
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault(){
        return RxBusHolder.INSTANCE;
    }

    private static class RxBusHolder{
        private static final RxBus INSTANCE = new RxBus();
    }

    //提供一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    //根据传递的EventType类型返回特定的eventType被观察者
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
