package com.lst.wanandroid.component;

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

    private void post(Object o) {
        bus.onNext(o);
    }

}
