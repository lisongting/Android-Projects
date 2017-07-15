package com.test.administrator.uitest1;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by lisongting on 2017/5/22.
 */

public class YoutuConnection {

    public static final String TAG = "YoutuConnection";
    private Context context;
    private Handler handler;
    private Bitmap userFace;
    private List<String> idList ;

    public YoutuConnection(Context context,Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    interface Callback{
        void onImageReady(Bitmap bitmap);
    }
    //获取某个已注册用户的人脸图像
    public Bitmap getUserFaceBitmap(String userId,final  Callback callback) {
        final String GET_USER_FACE_URL = "http://" + "100.66.6.236"+
                ":" + "8000" +
                "/face?userid=" + userId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response.Listener<JSONObject> rightListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            int ret = object.getInt("Ret");
                            String strFace = object.optString("Image");
                            if (ret == 0) {
                                //将base64的String 转换为合适大小的Bitmap
                                userFace = ImageUtils.decodeBase64ToBitmap(strFace);
                                callback.onImageReady(userFace);
                                Log.i(TAG, "userFace:"+userFace.getWidth() + "X" + userFace.getHeight());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(TAG, "getUserFaceBitmap()--Error:"+volleyError.getMessage());
                    }
                };
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        GET_USER_FACE_URL,
                        null,
                        rightListener,
                        errorListener);
                VolleySingleton.getVolleySingleton(context).addToRequestQueue(jsonObjectRequest);

            }
        }).start();
        return userFace;
    }



}
