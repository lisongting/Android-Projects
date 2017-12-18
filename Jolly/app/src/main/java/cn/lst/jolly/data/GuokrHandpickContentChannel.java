

package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuokrHandpickContentChannel {

    @ColumnInfo(name = "channel_url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "channel_date_created")
    @Expose
    @SerializedName("date_created")
    private String dateCreated;

    @ColumnInfo(name = "channel_name")
    @Expose
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "channel_key")
    @Expose
    @SerializedName("key")
    private String key;

    @ColumnInfo(name = "channel_articles_count")
    @Expose
    @SerializedName("articles_count")
    private int articlesCount;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(int articlesCount) {
        this.articlesCount = articlesCount;
    }
}
