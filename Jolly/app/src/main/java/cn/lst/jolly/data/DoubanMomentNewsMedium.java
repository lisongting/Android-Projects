package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoubanMomentNewsMedium {

    @ColumnInfo(name = "medium_url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "medium_width")
    @Expose
    @SerializedName("width")
    private int width;

    @ColumnInfo(name = "medium_height")
    @Expose
    @SerializedName("height")
    private int height;

    public DoubanMomentNewsMedium() {
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
