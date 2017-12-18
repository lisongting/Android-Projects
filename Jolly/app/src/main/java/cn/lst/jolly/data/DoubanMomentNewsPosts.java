package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.lst.jolly.database.converters.DoubanTypeConverter;

@Entity(tableName = "douban_moment_news")
@TypeConverters(DoubanTypeConverter.class)
public class DoubanMomentNewsPosts {

    @ColumnInfo(name = "display_style")
    @Expose
    @SerializedName("display_style")
    private int displayStyle;

    @ColumnInfo(name = "is_editor_choice")
    @Expose
    @SerializedName("is_editor_choice")
    private boolean isEditorChoice;

    @ColumnInfo(name = "published_time")
    @Expose
    @SerializedName("published_time")
    private String publishedTime;

    @ColumnInfo(name = "url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "short_url")
    @Expose
    @SerializedName("short_url")
    private String shortUrl;

    @ColumnInfo(name = "is_liked")
    @Expose
    @SerializedName("is_liked")
    private boolean isLiked;

    @Embedded
    @Expose
    @SerializedName("author")
    private DoubanMomentNewsAuthor author;

    @ColumnInfo(name = "column")
    @Expose
    @SerializedName("column")
    private String column;

    @ColumnInfo(name = "app_css")
    @Expose
    @SerializedName("app_css")
    private int appCss;

    @ColumnInfo(name = "abstract")
    @Expose
    @SerializedName("abstract")
    private String abs;

    @ColumnInfo(name = "date")
    @Expose
    @SerializedName("date")
    private String date;

    @ColumnInfo(name = "like_count")
    @Expose
    @SerializedName("like_count")
    private int likeCount;

    @ColumnInfo(name = "comments_count")
    @Expose
    @SerializedName("comments_count")
    private int commentsCount;

    @ColumnInfo(name = "thumbs")
    @Expose
    @SerializedName("thumbs")
    private List<DoubanMomentNewsThumbs> thumbs;

    @ColumnInfo(name = "created_time")
    @Expose
    @SerializedName("created_time")
    private String createdTime;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "share_pic_url")
    @Expose
    @SerializedName("share_pic_url")
    private String sharePicUrl;

    @ColumnInfo(name = "type")
    @Expose
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "id")
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "favorite")
    @Expose
    private boolean favorite;

    @ColumnInfo(name = "timestamp")
    @Expose
    private long timestamp;

    public int getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(int displayStyle) {
        this.displayStyle = displayStyle;
    }

    public boolean is_editor_choice() {
        return isEditorChoice;
    }

    public void setEditorChoice(boolean editorChoice) {
        this.isEditorChoice = editorChoice;
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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public boolean is_liked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public DoubanMomentNewsAuthor getAuthor() {
        return author;
    }

    public void setAuthor(DoubanMomentNewsAuthor author) {
        this.author = author;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getAppCss() {
        return appCss;
    }

    public void setAppCss(int appCss) {
        this.appCss = appCss;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEditorChoice() {
        return isEditorChoice;
    }

    public boolean isLiked() {
        return isLiked;
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
