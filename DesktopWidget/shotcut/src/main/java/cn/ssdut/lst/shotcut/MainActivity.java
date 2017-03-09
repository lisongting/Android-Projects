package cn.ssdut.lst.shotcut;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * [错误]
 * 照着书上的代码敲的，还是创建不了桌面快捷图标
 */
public class MainActivity extends AppCompatActivity {

    ImageView flower;
    Animation anim ,reverse;
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 123) {
                flower.startAnimation(reverse);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flower = (ImageView) findViewById(R.id.flower);
        anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        anim.setFillAfter(true);
        reverse = AnimationUtils.loadAnimation(this, R.anim.reverse);
        reverse.setFillAfter(true);
        Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //创建添加快捷方式的Intent
                Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                String title = "快捷图标";
                //加载快捷方式的图标
                Parcelable icon = Intent.ShortcutIconResource.fromContext(MainActivity.this, R.mipmap.ic_launcher);
                //创建点击快捷方式后对应的Intent,该处指定当点击创建快捷方式后，再次启动该程序
                Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                //设置图标的title
                intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
                //设置快捷方式的图标
                intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
                //设置快捷方式启动的Intent
                intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
                sendBroadcast(intent);

            }
        });

    }

    public void onResume() {
        super.onResume();
        flower.startAnimation(anim);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(123);
            }
        },3500);

    }
}
