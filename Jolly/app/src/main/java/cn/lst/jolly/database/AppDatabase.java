package cn.lst.jolly.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.GuokrHandpickContentResult;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.ZhihuDailyContent;
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;
import cn.lst.jolly.database.dao.DoubanMomentContentDao;
import cn.lst.jolly.database.dao.DoubanMomentNewsDao;
import cn.lst.jolly.database.dao.GuokrHandpickContentDao;
import cn.lst.jolly.database.dao.GuokrHandpickNewsDao;
import cn.lst.jolly.database.dao.ZhihuDailyContentDao;
import cn.lst.jolly.database.dao.ZhihuDailyNewsDao;

/**
 * Created by lisongting on 2017/12/18.
 */

@Database(entities = {
        ZhihuDailyNewsQuestion.class,
        DoubanMomentNewsPosts.class,
        GuokrHandpickNewsResult.class,
        ZhihuDailyContent.class,
        DoubanMomentContent.class,
        GuokrHandpickContentResult.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase{
    static final String DATABASE_NAME = "jolly-db";

    public abstract ZhihuDailyNewsDao zhihuDailyNewsDao();

    public abstract DoubanMomentNewsDao doubanMomentNewsDao();

    public abstract GuokrHandpickNewsDao guokrHandpickNewsDao();

    public abstract ZhihuDailyContentDao zhihuDailyContentDao();

    public abstract DoubanMomentContentDao doubanMomentContentDao();

    public abstract GuokrHandpickContentDao guokrHandpickContentDao();

}
