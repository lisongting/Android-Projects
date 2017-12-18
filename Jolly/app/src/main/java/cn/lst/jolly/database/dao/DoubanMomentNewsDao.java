
package cn.lst.jolly.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

import cn.lst.jolly.data.DoubanMomentNewsPosts;

@Dao
public interface DoubanMomentNewsDao {

    @Query("SELECT * FROM douban_moment_news WHERE timestamp BETWEEN (:timestamp - 24*60*60*1000 + 1) AND :timestamp ORDER BY timestamp ASC")
    List<DoubanMomentNewsPosts> queryAllByDate(long timestamp);

    @Query("SELECT * FROM douban_moment_news WHERE id = :id")
    DoubanMomentNewsPosts queryItemById(int id);

    @Query("SELECT * FROM douban_moment_news WHERE favorite = 1")
    List<DoubanMomentNewsPosts> queryAllFavorites();

    @Query("SELECT * FROM douban_moment_news WHERE (timestamp < :timestamp) AND (favorite = 0)")
    List<DoubanMomentNewsPosts> queryAllTimeoutItems(long timestamp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<DoubanMomentNewsPosts> items);

    @Update
    void update(DoubanMomentNewsPosts item);

    @Delete
    void delete(DoubanMomentNewsPosts item);

}
