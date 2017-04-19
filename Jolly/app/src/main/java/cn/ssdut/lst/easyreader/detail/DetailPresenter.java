package cn.ssdut.lst.easyreader.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.webkit.WebView;

import com.google.gson.Gson;

import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.DoubanMomentStory;
import cn.ssdut.lst.easyreader.bean.StringModelImpl;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyStory;
import cn.ssdut.lst.easyreader.db.DatabaseHelper;
import cn.ssdut.lst.easyreader.util.Api;

/**
 * Created by Administrator on 2017/4/6.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View view;
    private StringModelImpl model;
    private Context context;

    private ZhihuDailyStory zhihuDailyStory;
    private String guokrStory;
    private DoubanMomentStory doubanMomentStory;

    private SharedPreferences sp;
    private DatabaseHelper dbhelper;
    private Gson gson;

    private BeanType type;
    private int id;
    private String title;
    private String coverUrl;

    public void setType(BeanType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    //经典写法下：这些对象的创建是在Activity中创建的
    //采用MVP模式，这些对象的创建和初始化就不在原来的Activity(View层级)进行
    //View层级只需要持有Presenter对象就可以了
    public DetailPresenter(Context context, DetailContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        sp = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        dbhelper = new DatabaseHelper(context, "History.db", null, 5);
        gson = new Gson();
    }

    @Override
    public void openInBrowser() {
        if (checkNull()) {
            view.showLoadingError();
            return ;
        }
        try {
            Intent t = new Intent(Intent.ACTION_VIEW);
            switch (type) {
                case TYPE_ZHIHU:
                    t.setData(Uri.parse(ZhihuDailyStory.getShare_url()));
                    break;
                case TYPE_DOUBAN:
                    t.setData(Uri.parse(doubanMomentStory.getShort_url()));
                    break;
                case TYPE_GUOKR:
                    t.setData(Uri.parse(Api.GUOKR_ARTICLE_LINK_V1 + id));
                    break;
            }
            context.startActivity(t);
        } catch (android.content.ActivityNotFoundException e) {
            view.showBrowserNotFoundError();
        }

    }

    private boolean checkNull() {
    }

    @Override
    public void shareAsText() {
        if (checkNull()) {
            view.showSharingError();
            return;
        }
        try{
            Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            String shareText = "" + title + " ";
            switch (type) {
                case TYPE_ZHIHU:
                    shareText += zhihuDailyStory.getShare_url();
                    break;
                case TYPE_GUOKR:
                    shareText += Api.GUOKR_ARTICLE_LINK_V1 + id;
                    break;
                case TYPE_DOUBAN:
                    shareText += doubanMomentStory.getShort_url();

            }
            shareText += doubanMomentStory.getShort_url();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
        }catch (android.content.ActivityNotFoundException ex){
            view.showLoadingError();
        }
    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    @Override
    public void addToOrDeleteFromBookmarks() {

    }

    @Override
    public boolean queryIfIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void start() {

    }
}
