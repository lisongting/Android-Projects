package cn.ssdut.lst.easyreader.bean;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import cn.ssdut.lst.easyreader.app.VolleySingleton;
import cn.ssdut.lst.easyreader.interfaze.OnStringListener;

/**
 * Created by Administrator on 2017/3/28.
 */

public class StringModelImpl {
    private Context context;

    public StringModelImpl(Context context) {
        this.context = context;
    }

    public void load(String url, final OnStringListener listener) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
            }
        });

        //设置默认的重试模式
        request.setRetryPolicy(new DefaultRetryPolicy());

        //使用VolleySingleton单例维护一个请求队列
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
