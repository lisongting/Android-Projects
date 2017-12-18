
package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GuokrHandpickNewsAuthor {

    @ColumnInfo(name = "author_ukey")
    @Expose
    @SerializedName("ukey")
    private String ukey;

    @ColumnInfo(name = "author_is_title_authorized")
    @Expose
    @SerializedName("is_title_authorized")
    private String isTitleAuthorized;

    @ColumnInfo(name = "author_nickname")
    @Expose
    @SerializedName("nickname")
    private String nickname;

    @ColumnInfo(name = "author_master_category")
    @Expose
    @SerializedName("master_category")
    private String masterCategory;

    @ColumnInfo(name = "author_amended_reliability")
    @Expose
    @SerializedName("amended_reliability")
    private String amendedReliability;

    @ColumnInfo(name = "author_is_exists")
    @Expose
    @SerializedName("is_exists")
    private boolean isExists;

    @ColumnInfo(name = "author_title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "author_url")
    @Expose
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "author_gender")
    @Expose
    @SerializedName("gender")
    private String gender;

    @ColumnInfo(name = "author_followers_count")
    @Expose
    @SerializedName("followers_count")
    private int followersCount;

    @Embedded
    @Expose
    @SerializedName("avatar")
    private GuokrHandpickNewsAvatar avatar;

    @ColumnInfo(name = "author_resource_url")
    @Expose
    @SerializedName("resource_url")
    private String resourceUrl;

    public GuokrHandpickNewsAuthor() {
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public String getIsTitleAuthorized() {
        return isTitleAuthorized;
    }

    public void setIsTitleAuthorized(String isTitleAuthorized) {
        this.isTitleAuthorized = isTitleAuthorized;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMasterCategory() {
        return masterCategory;
    }

    public void setMasterCategory(String masterCategory) {
        this.masterCategory = masterCategory;
    }

    public String getAmendedReliability() {
        return amendedReliability;
    }

    public void setAmendedReliability(String amendedReliability) {
        this.amendedReliability = amendedReliability;
    }

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public GuokrHandpickNewsAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(GuokrHandpickNewsAvatar avatar) {
        this.avatar = avatar;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

}
