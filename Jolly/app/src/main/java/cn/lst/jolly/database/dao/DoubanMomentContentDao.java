
package cn.lst.jolly.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.lst.jolly.data.DoubanMomentContent;


@Dao
public interface DoubanMomentContentDao {

    @Query("SELECT * FROM douban_moment_content WHERE id = :id")
    DoubanMomentContent queryContentById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DoubanMomentContent content);

    @Update
    void update(DoubanMomentContent content);

    @Delete
    void delete(DoubanMomentContent content);

}
