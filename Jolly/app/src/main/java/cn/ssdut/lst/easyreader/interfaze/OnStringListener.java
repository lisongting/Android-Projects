package cn.ssdut.lst.easyreader.interfaze;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface OnStringListener {
    void onSuccess(String result);

    void onError(VolleyError error);
}
