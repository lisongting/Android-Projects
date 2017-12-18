
package cn.lst.jolly.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class DoubanMomentNews {

    @Expose
    @SerializedName("count")
    private int count;

    @Expose
    @SerializedName("posts")
    private List<DoubanMomentNewsPosts> posts;

    @Expose
    @SerializedName("offset")
    private int offset;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("total")
    private int total;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DoubanMomentNewsPosts> getPosts() {
        return posts;
    }

    public void setPosts(List<DoubanMomentNewsPosts> posts) {
        this.posts = posts;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
