package cn.ssdut.lst.easyreader.app;

import android.app.Application;
import android.content.ContentProvider;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Administrator on 2017/3/28.
 */

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        //0代表白天主题，1代表夜间主题
        if (getSharedPreferences("user_settings", MODE_PRIVATE).getInt("theme", 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        ContentProvider k;
    }

}
