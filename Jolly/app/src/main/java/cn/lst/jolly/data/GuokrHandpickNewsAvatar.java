
package cn.lst.jolly.data;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuokrHandpickNewsAvatar {

    @ColumnInfo(name = "avatar_large")
    @Expose
    @SerializedName("large")
    private String large;

    @ColumnInfo(name = "avatar_small")
    @Expose
    @SerializedName("small")
    private String small;

    @ColumnInfo(name = "avatar_normal")
    @Expose
    @SerializedName("normal")
    private String normal;

    public GuokrHandpickNewsAvatar() {}

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }
}
