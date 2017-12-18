package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoubanMomentNewsAuthor {

    @ColumnInfo(name = "is_followed")
    @Expose
    @SerializedName("is_followed")
    private boolean isFollowed;

    @ColumnInfo(name = "uid")
    @Expose
    @SerializedName("uid")
    private String uid;

    @ColumnInfo(name = "author_url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "author_avatar")
    @Expose
    @SerializedName("avatar")
    private String avatar;

    @ColumnInfo(name = "author_name")
    @Expose
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "author_is_special_user")
    @Expose
    @SerializedName("is_special_user")
    private boolean isSpecialUser;

    @ColumnInfo(name = "author_n_posts")
    @Expose
    @SerializedName("n_posts")
    public int nPosts;

    @ColumnInfo(name = "author_alt")
    @Expose
    @SerializedName("alt")
    private String alt;

    @ColumnInfo(name = "author_large_avatar")
    @Expose
    @SerializedName("large_avatar")
    private String largeAvatar;

    @ColumnInfo(name = "author_id")
    @Expose
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "author_is_auth_author")
    @Expose
    @SerializedName("is_auth_author")
    private boolean isAuthAuthor;

    public DoubanMomentNewsAuthor() {
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSpecialUser() {
        return isSpecialUser;
    }

    public void setSpecialUser(boolean specialUser) {
        isSpecialUser = specialUser;
    }

    public int getnPosts() {
        return nPosts;
    }

    public void setnPosts(int nPosts) {
        this.nPosts = nPosts;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAuthAuthor() {
        return isAuthAuthor;
    }

    public void setAuthAuthor(boolean authAuthor) {
        isAuthAuthor = authAuthor;
    }
}
