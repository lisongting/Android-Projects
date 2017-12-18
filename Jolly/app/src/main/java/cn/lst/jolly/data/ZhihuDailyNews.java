package cn.lst.jolly.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lisongting on 2017/12/18.
 */

public class ZhihuDailyNews {

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("stories")
    private List<ZhihuDailyNewsQuestion> stories;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDailyNewsQuestion> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuDailyNewsQuestion> stories) {
        this.stories = stories;
    }
}
