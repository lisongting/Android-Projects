
package cn.lst.jolly.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.lst.jolly.data.GuokrHandpickContentResult;


@Dao
public interface GuokrHandpickContentDao {

    @Query("SELECT * FROM guokr_handpick_content WHERE id = :id")
    GuokrHandpickContentResult queryContentById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GuokrHandpickContentResult content);

    @Update
    void update(GuokrHandpickContentResult content);

    @Delete
    void delete(GuokrHandpickContentResult item);
}
