package com.lst.wanandroid.core.event;

public class NightModeEvent {
    private boolean isNightMode;

    public NightModeEvent(boolean b){
        isNightMode = b;
    }

    public void setNightMode(boolean b) {
        isNightMode = b;
    }

    public boolean getNightMode(){
        return isNightMode;
    }
}
