package cn.ssdut.lst.ledlock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/9.
 */

//AppWidgetProvider继承了BroadcastReceiver类
    //所以LedLock实质上也是一个BroadcastReceiver
public class LedLock extends AppWidgetProvider {
    private Timer timer;
    private AppWidgetManager appWidgetManager;
    private Context context;
    private int[] digits = new int[]{
            R.drawable.su01,
            R.drawable.su02,
            R.drawable.su03,
            R.drawable.su04,
            R.drawable.su05,
            R.drawable.su06,
            R.drawable.su07,
            R.drawable.su08,
            R.drawable.su09,
            R.drawable.su10
    };

    //将显示小时，分钟，秒钟的ImageView定义成数组
    private int[] digitViews = new int[]{
            R.id.img01,
            R.id.img02,
            R.id.img04,
            R.id.img05,
            R.id.img07,
            R.id.img08,
    };

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 123) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
                Log.i("tag", "packageName:" + context.getPackageName());
                SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                //将当前时间转换为String形式
                String time = sdf.format(new Date());
                for(int i=0;i<time.length();i++) {
                    //将char转为int的acs码，相差48
                    int num = time.charAt(i) - 48;
                    //将第i个图片设为对应的液晶数字图片
                    views.setImageViewResource(digitViews[i],digits[num]);
                }
                //将AppWidgetProvider子类实例包装成ComponentName对象
                ComponentName componentName = new ComponentName(context, LedLock.class);
                //调用AppWidgetManager将remoteViews添加到ComponentName中
                appWidgetManager.updateAppWidget(componentName,views);
            }
            super.handleMessage(msg);
        }
    };
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        this.appWidgetManager = appWidgetManager;
        this.context = context;
        //启动定时器
        timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                handler.sendEmptyMessage(123);
            }
        },0,1000);
    }

}
