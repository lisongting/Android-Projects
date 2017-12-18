
package cn.lst.jolly.data;

import android.arch.persistence.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuokrHandpickContent {

    @Expose
    @SerializedName("now")
    private String now;

    @Expose
    @SerializedName("ok")
    private boolean ok;

    @Embedded
    @Expose
    @SerializedName("result")
    private GuokrHandpickContentResult result;

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

    public GuokrHandpickContentResult getResult() {
        return result;
    }

    public void setResult(GuokrHandpickContentResult result) {
        this.result = result;
    }

}
