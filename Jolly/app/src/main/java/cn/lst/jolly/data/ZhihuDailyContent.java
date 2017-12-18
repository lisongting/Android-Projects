package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.lst.jolly.database.converters.StringTypeConverter;

/**
 * Created by lisongting on 2017/12/18.
 */

@Entity(tableName = "zhihu_daily_content")
@TypeConverters(StringTypeConverter.class)
public class ZhihuDailyContent {
    @ColumnInfo(name="body")
    @Expose
    @SerializedName("body")
    private String body;


    @ColumnInfo(name="image_source")
    @Expose
    @SerializedName("image_source")
    private String imageSource;

    @ColumnInfo(name="title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "image")
    @Expose
    @SerializedName("image")
    private String image;

    @ColumnInfo(name = "share_url")
    @Expose
    @SerializedName("share_url")
    private String shareUrl;

    @ColumnInfo(name = "js")
    @Expose
    @SerializedName("js")
    private List<String> js;

    @Ignore // This field will be ignored.
    @Expose
    @SerializedName("ga_prefix")
    private String gaPrefix;

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

    @ColumnInfo(name = "css")
    @Expose
    @SerializedName("css")
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

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

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

}
