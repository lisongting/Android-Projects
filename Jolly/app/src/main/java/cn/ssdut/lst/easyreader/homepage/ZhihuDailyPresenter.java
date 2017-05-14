package cn.ssdut.lst.easyreader.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.StringModelImpl;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.db.DatabaseHelper;
import cn.ssdut.lst.easyreader.detail.DetailActivity;
import cn.ssdut.lst.easyreader.interfaze.OnStringListener;
import cn.ssdut.lst.easyreader.util.Api;
import cn.ssdut.lst.easyreader.util.DateFormatter;
import cn.ssdut.lst.easyreader.util.NetworkState;

import static cn.ssdut.lst.easyreader.service.CacheService.TYPE_ZHIHU;

/**
 * Created by Administrator on 2017/3/28.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {
    private ZhihuDailyContract.View view;
    private Context context;
    private StringModelImpl model;

    private DateFormatter formatter = new DateFormatter();
    private Gson gson = new Gson();

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private List<ZhihuDailyNews.Question> list = new ArrayList<>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        dbHelper = new DatabaseHelper(context, "history.db", null, 5);
        db = dbHelper.getWritableDatabase();
    }
    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing) {
            view.showLoading();
        }

        if (NetworkState.networkConnected(context)) {

            //发起网络请求，将long型的date传入，拼接在知乎API的后面
            model.load(Api.ZHIHU_HISTORY + formatter.ZhihuDailyDateFormat(date),
                    new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                ZhihuDailyNews post = gson.fromJson(result, ZhihuDailyNews.class);
                                ContentValues values = new ContentValues();
                                if (clearing) {
                                    //清除list中存放的ZhihuDailyNews.Question
                                    list.clear();
                                }

                                for (ZhihuDailyNews.Question item: post.getStories()){
                                    //添加到list中
                                    list.add(item);
                                    if (!queryIfIDExist(item.getId())) {
                                        //向数据库中插入数据
                                        db.beginTransaction();
                                        SimpleDateFormat format = new SimpleDateFormat("yyyyDDmm");
                                        Date date = format.parse(post.getDate());
                                        values.put("zhihu_id", item.getId());
                                        values.put("zhihu_news",gson.toJson(item));
                                        values.put("zhihu_content", "");
                                        //返回的是毫秒数，然后除以1000
                                        values.put("zhihu_time", date.getTime() / 1000);
                                        db.insert("Zhihu", null, values);
                                        db.setTransactionSuccessful();
                                        db.endTransaction();
                                        values.clear();
                                    }

                                    //通过发送广播，唤醒CacheService中的BroadcastReceiver，然后该BroadcastReceiver根据广播的内容
                                    // 来决定调用哪一个缓存函数
                                    Intent intent = new Intent("cn.lst.jolly.LOCAL_BROADCAST");
                                    intent.putExtra("type", TYPE_ZHIHU);
                                    intent.putExtra("id", item.getId());
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                }
                                view.showResult(list);


                            } catch (JsonSyntaxException e){
                                view.showError();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            view.stopLoading();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.stopLoading();
                            view.showError();

                        }
                    });
        }else{
            //如果网络不可用，就从缓存数据库中取出数据
            if (clearing) {
                list.clear();
                Cursor cursor = db.query("Zhihu", null, null, null, null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        //将缓存数据库中的知乎新闻取出来，转换成Question对象
                        ZhihuDailyNews.Question question = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news"))
                                , ZhihuDailyNews.Question.class);
                        list.add(question);
                    }while(cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResult(list);
            }else{
                view.showError();
            }
        }

    }
    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {
        Intent t = new Intent(context, DetailActivity.class);
        t.putExtra("type", BeanType.TYPE_ZHIHU)
                .putExtra("id",list.get(position).getId())
                .putExtra("title",list.get(position).getTitle())
                .putExtra("coverUrl",list.get(position).getImages().get(0));
        context.startActivity(t);
    }

    @Override
    public void feelLucky() {
        if (list.isEmpty()) {
            view.showError();
            return ;
        }
        //随机读一篇文章
        startReading(new Random().nextInt(list.size()));
    }

    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    private boolean queryIfIDExist(int id) {
        Cursor cursor = db.query("Zhihu", new String[]{"zhihu_id"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }
}
