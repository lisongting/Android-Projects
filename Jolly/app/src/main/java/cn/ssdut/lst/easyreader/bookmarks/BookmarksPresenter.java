package cn.ssdut.lst.easyreader.bookmarks;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import cn.ssdut.lst.easyreader.adapter.BookmarksAdapter;
import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.db.DatabaseHelper;
import cn.ssdut.lst.easyreader.detail.DetailActivity;

import static cn.ssdut.lst.easyreader.adapter.BookmarksAdapter.TYPE_DOUBAN_NORMAL;
import static cn.ssdut.lst.easyreader.adapter.BookmarksAdapter.TYPE_DOUBAN_WITH_HEADER;
import static cn.ssdut.lst.easyreader.adapter.BookmarksAdapter.TYPE_GUOKR_NORMAL;
import static cn.ssdut.lst.easyreader.adapter.BookmarksAdapter.TYPE_GUOKR_WITH_HEADER;
import static cn.ssdut.lst.easyreader.adapter.BookmarksAdapter.TYPE_ZHIHU_NORMAL;
import static cn.ssdut.lst.easyreader.adapter.BookmarksAdapter.TYPE_ZHIHU_WITH_HEADER;

/**
 * Created by lisongting on 2017/5/12.
 */

public class BookmarksPresenter implements BookmarksContract.Presenter {
    private BookmarksContract.View view;
    private Context context;
    private Gson gson;

    private ArrayList<ZhihuDailyNews.Question> zhihuList;
    private ArrayList<DoubanMomentNews.posts> doubanList;
    private ArrayList<GuokrHandpickNews.result> guokrList;

    private ArrayList<Integer> types;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public BookmarksPresenter(Context context, BookmarksContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        gson = new Gson();
        dbHelper = new DatabaseHelper(context, "History.db", null, 5);
        db = dbHelper.getWritableDatabase();
        zhihuList = new ArrayList<>();
        doubanList = new ArrayList<>();
        guokrList = new ArrayList<>();
        types = new ArrayList<>();
    }
    @Override
    public void start() {

    }

    @Override
    public void loadResults(boolean refresh) {
        if (!refresh) {
            view.showLoading();
        } else {
            zhihuList.clear();
            doubanList.clear();
            guokrList.clear();
            types.clear();
        }
        checkForFreshData();
        view.showResults(zhihuList, guokrList, doubanList, types);
        view.stopLoading();
    }

    @Override
    public void startReading(BeanType type, int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        switch (type) {
            case TYPE_ZHIHU:
                ZhihuDailyNews.Question q = zhihuList.get(position - 1);
                intent.putExtra("type", BeanType.TYPE_ZHIHU);
                intent.putExtra("id", q.getId());
                intent.putExtra("title", q.getTitle());
                intent.putExtra("coverUrl", q.getImages().get(0));
                break;
            case TYPE_GUOKR:
                GuokrHandpickNews.result r = guokrList.get(position - zhihuList.size() - 2);
                intent.putExtra("type", BeanType.TYPE_GUOKR);
                intent.putExtra("id", r.getId());
                intent.putExtra("title", r.getTitle());
                intent.putExtra("coverUrl", r.getHeadline_img());
                break;
            case TYPE_DOUBAN:
                DoubanMomentNews.posts p = doubanList.get(position - zhihuList.size() - guokrList.size() - 3);
                intent.putExtra("type", BeanType.TYPE_DOUBAN);
                intent.putExtra("id", p.getId());
                intent.putExtra("title", p.getTitle());
                if (p.getThumbs().size() == 0){
                    intent.putExtra("coverUrl", "");
                } else {
                    intent.putExtra("image", p.getThumbs().get(0).getMedium().getUrl());
                }
                break;
            default:
                break;
        }
        context.startActivity(intent);
    }

    @Override
    public void checkForFreshData() {
        types.add(TYPE_ZHIHU_WITH_HEADER);
        Cursor cursor = db.rawQuery("select * from Zhihu where bookmark = ?", new String[]{"1"});
        Log.i("tag","zhihu bookmark count:"+ cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                ZhihuDailyNews.Question q = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDailyNews.Question.class);
                zhihuList.add(q);
                types.add(TYPE_ZHIHU_NORMAL);
            } while (cursor.moveToNext());
        }
        types.add(TYPE_GUOKR_WITH_HEADER);
        cursor = db.rawQuery("select * from Guokr where bookmark = ?", new String[]{"1"});
        Log.i("tag","guokr bookmark count:"+ cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                GuokrHandpickNews.result result = gson.fromJson(cursor.getString(cursor.getColumnIndex("guokr_news")), GuokrHandpickNews.result.class);
                guokrList.add(result);
                types.add(TYPE_GUOKR_NORMAL);
            } while (cursor.moveToNext());
        }

        types.add(TYPE_DOUBAN_WITH_HEADER);
        cursor = db.rawQuery("select * from Douban where bookmark = ?", new String[]{"1"});
        Log.i("tag","douban bookmark count:"+ cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                DoubanMomentNews.posts post = gson.fromJson(cursor.getString(cursor.getColumnIndex("douban_news")), DoubanMomentNews.posts.class);
                doubanList.add(post);
                types.add(TYPE_DOUBAN_NORMAL);
            } while (cursor.moveToNext());
        }

        cursor.close();

    }

    @Override
    public void feelLucky() {
        Random random = new Random();
        int p = random.nextInt(types.size());
        while (true) {
            if (types.get(p) == BookmarksAdapter.TYPE_ZHIHU_NORMAL) {
                startReading(BeanType.TYPE_ZHIHU, p);
                break;
            } else if (types.get(p) == TYPE_GUOKR_NORMAL) {
                startReading(BeanType.TYPE_GUOKR, p);
                break;
            } else if (types.get(p) == TYPE_DOUBAN_NORMAL) {
                startReading(BeanType.TYPE_DOUBAN, p);
                break;
            } else {
                p = random.nextInt(types.size());
            }
        }
    }
}
