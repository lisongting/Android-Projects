package com.example.rostest.lst_try;

/**
 * Created by lisongting on 2017/6/4.
 */


public class TtsStatus {
    //当前正在播放的音频的id
    private int id;
    //当前的播放状态
    private boolean isplaying;

    public TtsStatus(int id, boolean isplaying) {
        this.id = id;
        this.isplaying = isplaying;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isplaying() {
        return isplaying;
    }

    public void setIsplaying(boolean isplaying) {
        this.isplaying = isplaying;
    }

    public String toString() {
        return "[" + id + "," + isplaying + "]";
    }
}
