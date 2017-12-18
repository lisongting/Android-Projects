package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.lst.jolly.database.converters.DoubanTypeConverter;

/**
 * Created by lisongting on 2017/12/18.
 */
@Entity(tableName = "douban_moment_content")
@TypeConverters(DoubanTypeConverter.class)
public class DoubanMomentContent {
    @ColumnInfo(name = "display_style")
    @Expose
    @SerializedName("display_style")
    private int displayStyle;

    @ColumnInfo(name = "short_url")
    @Expose
    @SerializedName("short_url")
    private String shortUrl;

    @ColumnInfo(name = "abstract")
    @Expose
    @SerializedName("abstract")
    private String abs;

    @ColumnInfo(name = "app_css")
    @Expose
    @SerializedName("app_css")
    private int appCss;

    @ColumnInfo(name = "like_count")
    @Expose
    @SerializedName("like_count")
    private int likeCount;

    @ColumnInfo(name = "thumbs")
    @Expose
    @SerializedName("thumbs")
    private List<DoubanMomentNewsThumbs> thumbs;

    @ColumnInfo(name = "created_time")
    @Expose
    @SerializedName("created_time")
    private String createdTime;

    @ColumnInfo(name = "id")
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "is_editor_choice")
    @Expose
    @SerializedName("is_editor_choice")
    private boolean isEditorChoice;

    @ColumnInfo(name = "original_url")
    @Expose
    @SerializedName("original_url")
    private String originalUrl;

    @ColumnInfo(name = "content")
    @Expose
    @SerializedName("content")
    private String content;

    @ColumnInfo(name = "share_pic_url")
    @Expose
    @SerializedName("share_pic_url")
    private String sharePicUrl;

    @ColumnInfo(name = "type")
    @Expose
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "is_liked")
    @Expose
    @SerializedName("is_liked")
    private boolean isLiked;

    @ColumnInfo(name = "photos")
    @Expose
    @SerializedName("photos")
    private List<DoubanMomentNewsThumbs> photos;

    @ColumnInfo(name = "published_time")
    @Expose
    @SerializedName("published_time")
    private String publishedTime;

    @ColumnInfo(name = "url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "column")
    @Expose
    @SerializedName("column")
    private String column;

    @ColumnInfo(name = "comments_count")
    @Expose
    @SerializedName("comments_count")
    private int commentsCount;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    public int getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(int displayStyle) {
        this.displayStyle = displayStyle;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public int getAppCss() {
        return appCss;
    }

    public void setAppCss(int appCss) {
        this.appCss = appCss;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<DoubanMomentNewsThumbs> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<DoubanMomentNewsThumbs> thumbs) {
        this.thumbs = thumbs;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEditorChoice() {
        return isEditorChoice;
    }

    public void setEditorChoice(boolean editorChoice) {
        isEditorChoice = editorChoice;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSharePicUrl() {
        return sharePicUrl;
    }

    public void setSharePicUrl(String sharePicUrl) {
        this.sharePicUrl = sharePicUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public List<DoubanMomentNewsThumbs> getPhotos() {
        return photos;
    }

    public void setPhotos(List<DoubanMomentNewsThumbs> photos) {
        this.photos = photos;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
