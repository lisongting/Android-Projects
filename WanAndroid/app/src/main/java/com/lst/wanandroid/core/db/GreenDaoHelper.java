package com.lst.wanandroid.core.db;

import com.lst.wanandroid.WanAndroidApp;
import com.lst.wanandroid.core.dao.DaoSession;
import com.lst.wanandroid.core.dao.HistoryData;
import com.lst.wanandroid.core.dao.HistoryDataDao;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

public class GreenDaoHelper implements DbHelper {
    private DaoSession daoSession;

    //todo:?
    @Inject
    GreenDaoHelper(){
        daoSession = WanAndroidApp.getInstance().getDaoSession();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();
        List<HistoryData> historyDataList = historyDataDao.loadAll();//查询所有数据库中的条目
        HistoryData historyData = new HistoryData();
        historyData.setDate(System.currentTimeMillis());
        historyData.setData(data);
        Iterator<HistoryData> iterator = historyDataList.iterator();
        while (iterator.hasNext()) {
            HistoryData tmp = iterator.next();
            //如果存在相同的数据条目，但时间不同
            //将旧的删除，更新为新的数据库条目
            if (tmp.getData().equals(data)) {
                historyDataList.remove(tmp);
                historyDataList.add(historyData);
                historyDataDao.deleteAll();
                historyDataDao.insertInTx(historyDataList);
                return historyDataList;
            }
        }
        //如果数目大于10，则移除第一个数据条目
        if (historyDataList.size() < 10) {
            historyDataDao.insert(historyData);
        }else {
            historyDataList.remove(0);
            historyDataList.add(historyData);
            historyDataDao.deleteAll();
            historyDataDao.insertInTx(historyDataList);
        }
        return historyDataList;
    }

    @Override
    public void clearHistoryData() {
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();
        historyDataDao.deleteAll();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return daoSession.getHistoryDataDao().loadAll();
    }
}
