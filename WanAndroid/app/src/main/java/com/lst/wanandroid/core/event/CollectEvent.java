package com.lst.wanandroid.core.event;

public class CollectEvent {
    private boolean isCancelCollectSuccess;

    public CollectEvent(boolean isCancelCollectSuccess) {
        this.isCancelCollectSuccess = isCancelCollectSuccess;
    }

    public boolean isCancelCollectSuccess(){
        return isCancelCollectSuccess;
    }

    public void setCancelCollectSuccess(boolean b){
        this.isCancelCollectSuccess = b;
    }
}
