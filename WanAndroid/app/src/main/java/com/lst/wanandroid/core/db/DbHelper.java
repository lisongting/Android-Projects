package com.lst.wanandroid.core.db;

import com.lst.wanandroid.core.dao.HistoryData;

import java.util.List;

public interface DbHelper {

    List<HistoryData> addHistoryData(String data);

    void clearHistoryData();

    List<HistoryData> loadAllHistoryData();
}
