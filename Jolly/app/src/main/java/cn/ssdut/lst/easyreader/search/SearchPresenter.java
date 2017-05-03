package cn.ssdut.lst.easyreader.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;

import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.db.DatabaseHelper;

/**
 * Created by lisongting on 2017/4/30.
 */

public class SearchPresenter implements SearchContract.Presenter {
    public static final int TYPE_ZHIHU_NORMAL = 0;
    public static final int TYPE_ZHIHU_WITH_HEADER = 1;
    public static final int TYPE_GUOKR_NORMAL = 2;
    public static final int TYPE_GUOKR_WITH_HEADER = 3;
    public static final int TYPE_DOUBAN_NORMAL = 4;
    public static final int TYPE_DOUBAN_WITH_HEADER = 5;

    private SearchContract.View view ;
    private Context context;
    private Gson gson;

    private ArrayList<ZhihuDailyNews.Question> zhihuList;
    private ArrayList<GuokrHandpickNews.result> guokrList;
    private ArrayList<DoubanMomentNews.posts> doubanList;
    private ArrayList<Integer> types;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public SearchPresenter(Context context, SearchContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        gson = new Gson();
        dbHelper = new DatabaseHelper(context, "history.db", null, 5);
        db = dbHelper.getWritableDatabase();

        zhihuList = new ArrayList<>();
        guokrList = new ArrayList<>();
        doubanList = new ArrayList<>();

        types = new ArrayList<>();

    }
    @Override
    public void start() {

    }

    @Override
    public void loadResult(String queryWords) {
        zhihuList.clear();
        guokrList.clear();
        doubanList.clear();
        types.clear();

        types.add(TYPE_ZHIHU_WITH_HEADER);
        Cursor cursor = db.query("select * from ") ;

    }

    @Override
    public void startReading(BeanType type, int pos) {

    }
}
