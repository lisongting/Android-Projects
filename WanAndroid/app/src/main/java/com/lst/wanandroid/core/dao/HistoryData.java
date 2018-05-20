package com.lst.wanandroid.core.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class HistoryData {
    private long date;
    private String data;
    @Generated(hash = 1452354993)
    public HistoryData(long date, String data) {
        this.date = date;
        this.data = data;
    }
    @Generated(hash = 422767273)
    public HistoryData() {
    }
    public long getDate() {
        return this.date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }


}
