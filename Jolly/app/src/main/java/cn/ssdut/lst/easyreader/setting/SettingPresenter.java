package cn.ssdut.lst.easyreader.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.preference.Preference;

import com.bumptech.glide.Glide;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/5/3.
 */

public class SettingPresenter implements SettingContract.Presenter {

    private Context context;
    private SettingContract.View view;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static final int CLEAR_GLIDE_CACHE_DONE = 1;

    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAR_GLIDE_CACHE_DONE:
                    view.showCleanGlideCacheDone();
                    break;
                default:
                    break;
            }
        }
    };

    public SettingPresenter(Context context, SettingContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        sp = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void start() {

    }

    @Override
    public void setNoPictureMode(Preference preference) {
        //这里其实没必要再来一个SharedPreference，因为原来的Preference在设置的时候已经写入到系统底层的SharedPreference中了

//       实际使用的时候可以这样：
//       SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//       然后就可以调用一系列的get方法，由key拿到相应的value
//       如： pref.getBoolean(key, false);
//       如：pref.getInt(key, 0);

        editor.putBoolean("no_picture_mode", preference.getSharedPreferences().getBoolean("no_picture_mode", false));
        editor.apply();
    }

    @Override
    public void setInAppBrowser(Preference preference) {
        editor.putBoolean("in_app_browser", preference.getSharedPreferences().getBoolean("in_app_browser", false));
        editor.apply();
    }

    @Override
    public void cleanGlideCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                Message msg = new Message();
                msg.what = CLEAR_GLIDE_CACHE_DONE;
                handler.sendMessage(msg);
            }
        }).start();

        Glide.get(context).clearMemory();
    }

    @Override
    public void setTimeOfSavingArticles(Preference preference, Object newValue) {
        editor.putString("time_of_saving_articles", (String) newValue);
        editor.apply();
    }

    @Override
    public String getTimeSummary() {
        String[] options = context.getResources().getStringArray(R.array.time_to_save_opts);
        String str = sp.getString("time_of_saving_articles", "7");
        switch (str) {
            case "1":
                return options[0];
            case "3":
                return options[1];
            case "15":
                return options[3];
            default:
                return options[2];
        }
    }
}
