

package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DoubanMomentNewsThumbs {

    @Embedded
    @Expose
    @SerializedName("medium")
    private DoubanMomentNewsMedium medium;

    @ColumnInfo(name = "thumb_description")
    @Expose
    @SerializedName("description")
    private String description;

    @Embedded
    @Expose
    @SerializedName("large")
    private DoubanMomentNewsLarge large;

    @Expose
    @SerializedName("tag_name")
    private String tagName;

    @Embedded
    @Expose
    @SerializedName("small")
    private DoubanMomentNewsSmall small;

    @ColumnInfo(name = "thumb_id")
    @Expose
    @SerializedName("id")
    private int id;

    public DoubanMomentNewsThumbs() {
    }

    public DoubanMomentNewsMedium getMedium() {
        return medium;
    }

    public void setMedium(DoubanMomentNewsMedium medium) {
        this.medium = medium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DoubanMomentNewsLarge getLarge() {
        return large;
    }

    public void setLarge(DoubanMomentNewsLarge large) {
        this.large = large;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tag_name) {
        this.tagName = tag_name;
    }

    public DoubanMomentNewsSmall getSmall() {
        return small;
    }

    public void setSmall(DoubanMomentNewsSmall small) {
        this.small = small;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
