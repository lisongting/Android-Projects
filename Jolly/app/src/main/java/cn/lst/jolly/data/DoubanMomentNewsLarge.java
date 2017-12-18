package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoubanMomentNewsLarge {

    @ColumnInfo(name = "large_url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "large_width")
    @Expose
    @SerializedName("width")
    private int width;

    @ColumnInfo(name = "large_height")
    @Expose
    @SerializedName("height")
    private int height;

    public DoubanMomentNewsLarge() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
