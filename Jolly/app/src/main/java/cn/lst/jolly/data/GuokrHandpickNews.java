
package cn.lst.jolly.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GuokrHandpickNews {

    @Expose
    @SerializedName("now")
    private String now;

    @Expose
    @SerializedName("ok")
    private boolean ok;

    @Expose
    @SerializedName("limit")
    private int limit;

    @Expose
    @SerializedName("result")
    private List<GuokrHandpickNewsResult> result;

    @Expose
    @SerializedName("offset")
    private int offset;

    @Expose
    @SerializedName("total")
    private int total;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GuokrHandpickNewsResult> getResult() {
        return result;
    }

    public void setResult(List<GuokrHandpickNewsResult> result) {
        this.result = result;
    }

}
