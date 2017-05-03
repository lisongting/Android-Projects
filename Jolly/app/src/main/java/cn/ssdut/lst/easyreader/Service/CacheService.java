package cn.ssdut.lst.easyreader.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Calendar;

import cn.ssdut.lst.easyreader.app.VolleySingleton;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyStory;
import cn.ssdut.lst.easyreader.db.DatabaseHelper;
import cn.ssdut.lst.easyreader.util.Api;

/**
 * Created by lisongting on 2017/4/30.
 */

public class CacheService extends Service {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public static final String TAG = CacheService.class.getSimpleName();

    public static final int TYPE_ZHIHU  = 0x00;
    public static final int TYPE_GUOKR  = 0x01;
    public static final int TYPE_DOUBAN = 0x02;


    public void onCreate() {
        super.onCreate();
        dbHelper = new DatabaseHelper(this, "history.db", null, 5);
        db = dbHelper.getWritableDatabase();

        //动态注册的广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction("cn.lst.zhihudaily.LOCAL_BROADCAST");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(new LocalReceiver(), filter);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 网络请求id对应的知乎日报的内容主体
     * 当type为0时，存储body中的数据
     * 当type为1时，再次请求share url中的内容并储存
     * id :所要获取的知乎日报消息内容对应的id
     */
    public void startZhihuCache(final int id) {
        Cursor cursor = db.query("Zhihu", null, null, null, null, null, null);

        //连续嵌套的Volley请求，这样会造成迷之缩进
        if (cursor.moveToFirst()) {
            do {
                if ((cursor.getInt(cursor.getColumnIndex("zhihu_id")) == id) &&
                        (cursor.getString(cursor.getColumnIndex("zhihu_content")).equals(""))) {
                    StringRequest request = new StringRequest(
                            Request.Method.GET,
                            Api.ZHIHU_NEWS + id,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    Gson gson = new Gson();
                                    ZhihuDailyStory story = gson.fromJson(s, ZhihuDailyStory.class);

                                    //如果等于1则请求share_url中的数据并存储
                                    if (story.getType() == 1) {
                                        Response.Listener rightListener = new Response.Listener() {
                                            @Override
                                            public void onResponse(Object o) {
                                                ContentValues values = new ContentValues();
                                                values.put("zhihu_content", (String) o);
                                                db.update("zhihu", values, "zhihu_id=?", new String[]{String.valueOf(id)});
                                                values.clear();
                                            }
                                        };
                                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                            }
                                        };
                                        StringRequest request1 = new StringRequest(Request.Method.GET,
                                                story.getShare_url(),
                                                rightListener,
                                                errorListener);
                                        request1.setTag(TAG);
                                        VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(request1);
                                    } else if (story.getType() == 0) {
                                        ContentValues values = new ContentValues();
                                        values.put("zhihu_content", s);
                                        db.update("Zhihu", values, "zhihu_id=?", new String[]{String.valueOf(id)});
                                        values.clear();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            });

                    request.setTag(TAG);
                    VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(request);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    //网络请求豆瓣精选的内容主体并进行缓存
    private void startDoubanCache(final int id) {
        Cursor cursor = db.query("Douban", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if ((cursor.getInt(cursor.getColumnIndex("douban_id")) == id) &&
                        (cursor.getString(cursor.getColumnIndex("douban_content")).equals(""))) {
                    StringRequest request = new StringRequest(
                            Api.DOUBAN_ARTICLE_DETAIL + id,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    ContentValues values = new ContentValues();
                                    values.put("douban_content", s);
                                    db.update("Douban", values, "douban_id=?", new String[]{String.valueOf(id)});
                                    values.clear();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            }
                    );
                    request.setTag(TAG);
                    VolleySingleton.getVolleySingleton(CacheService.this).getRequestQueue().add(request);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void startGuokrCache(final int id) {
        Cursor cursor = db.query("Guokr", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if ((cursor.getInt(cursor.getColumnIndex("guokr_id")) == id)
                        && (cursor.getString(cursor.getColumnIndex("guokr_content")).equals(""))) {
                    StringRequest request = new StringRequest(Api.GUOKR_ARTICLE_LINK_V1 + id, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            ContentValues values = new ContentValues();
                            values.put("guokr_content", s);
                            db.update("Guokr", values, "guokr_id = ?", new String[] {String.valueOf(id)});
                            values.clear();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
                    request.setTag(TAG);
                    VolleySingleton.getVolleySingleton(CacheService.this).getRequestQueue().add(request);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void onDestroy() {
        super.onDestroy();
        VolleySingleton.getVolleySingleton(this).getRequestQueue().cancelAll(TAG);
        deleteTimeoutPosts();
    }


    private void deleteTimeoutPosts() {
        SharedPreferences sp = getSharedPreferences("user_settings", MODE_PRIVATE);
        Calendar c = Calendar.getInstance();
        long timeStamp = (c.getTimeInMillis() / 1000) - Long.parseLong(sp.getString("time_of_saving_articles", "7")) * 24 * 3600;

        String[] whereClause = new String[]{String.valueOf(timeStamp)};
        db.delete("Zhihu", "zhihu_time < ? and bookMark !=1", whereClause);
        db.delete("Zhihu", "guokr_time < ? and bookMark !=1", whereClause);
        db.delete("Zhihu", "douban_time < ? and bookMark !=1", whereClause);

    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", 0);
            switch (intent.getIntExtra("type", -1)) {
                case TYPE_ZHIHU:
                    startZhihuCache(id);
                    break;
                case TYPE_DOUBAN:
                    startDoubanCache(id);
                    break;
                case TYPE_GUOKR:
                    startGuokrCache(id);
                    break;
                default:
                    break;
            }
        }
    }



}
