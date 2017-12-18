package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.lst.jolly.database.converters.StringTypeConverter;


@Entity(tableName = "zhihu_daily_news")
@TypeConverters(StringTypeConverter.class)
public class ZhihuDailyNewsQuestion {

    @ColumnInfo(name = "images")
    @Expose
    @SerializedName("images")
    private List<String> images;

    @ColumnInfo(name = "type")
    @Expose
    @SerializedName("type")
    private int type;

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "ga_prefix")
    @Expose
    @SerializedName("ga_prefix")
    private String gaPrefix;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "favorite")
    @Expose
    private boolean favorite;

    @ColumnInfo(name = "timestamp")
    @Expose
    private long timestamp;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
