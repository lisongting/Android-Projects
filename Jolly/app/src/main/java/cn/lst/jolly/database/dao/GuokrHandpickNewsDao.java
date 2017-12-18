package cn.lst.jolly.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.lst.jolly.data.GuokrHandpickNewsResult;

/**
 * Created by lisongting on 2017/12/18.
 */
@Dao
public interface GuokrHandpickNewsDao {
    //表示从查询结果集中，跳过offset条数据，然后取limit条数据
    @Query("SELECT * FROM guokr_handpick_news LIMIT:limit OFFSET :offset")
    List<GuokrHandpickNewsResult> queryAllByOffsetAndLimit(int offset,int limit);

    @Query("SELECT * FROM guokr_handpick_news WHERE id=:id")
    GuokrHandpickNewsResult queryItemById(int id);

    @Query("SELECT * FROM guokr_handpick_news WHERE favorite = 1")
    List<GuokrHandpickNewsResult> queryAllFavorite();

    @Query("SELECT * FROM guokr_handpick_news WHERE (timestamp < :timestamp) AND (favorite =0)")
    List<GuokrHandpickNewsResult> queryAllTimeoutItems(long timestamp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<GuokrHandpickNewsResult> items);

    @Update
    void update(GuokrHandpickNewsResult item);

    @Delete
    void delete(GuokrHandpickNewsResult item);
}
