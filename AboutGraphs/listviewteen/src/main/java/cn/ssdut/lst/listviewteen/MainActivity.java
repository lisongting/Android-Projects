package cn.ssdut.lst.listviewteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.listView);
       WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        final MyAnimation animation = new MyAnimation(metrics.xdpi/2,metrics.ydpi/2,3500);
        listView.setAnimation(animation);//只有被定义为final 的变量才能在匿名内部类中使用
        //我想定义一个定时器，当动画播放完时自动重新演示动画，但是没成功
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                listView.setAnimation(animation);
//            }
//        };
//        new Timer().schedule(timerTask,3500,4000);
    }
}
