package cn.ssdut.lst.musicbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView title,author;
    ImageButton play,stop;
    ActivityReceiver activityReceiver;
    public static final String CTL_ACTION ="cn.ssdut.lst.CTL_ACTION";
    public static final String UPDATE_ACTION="CN.ssdut.lst.UPDATE_ACTION";
    //定义播放状态，0x11代表没有播放，Ox12代表正在播放，Ox13代表暂停播放
    int status = 0x11;
    String[] titleStrs = new String[]{"心愿","约定","美丽新世界"};
    String[] authorStrs = new String[]{"未知","周惠","五百"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (ImageButton) this.findViewById(R.id.play);
        stop = (ImageButton) this.findViewById(R.id.stop);
        title = (TextView) findViewById(R.id.title);
        author = (TextView) findViewById(R.id.author);
        // 为两个按钮的单击事件添加监听器
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        activityReceiver = new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        //指定BroadcastReceiver监听的action
        filter.addAction(UPDATE_ACTION);
        //注册BroadcastReceiver
        registerReceiver(activityReceiver,filter);
        Intent intent = new Intent(MainActivity.this,MusicService.class);
        startService(intent);//启动后台的Service

    }

    //自定义的BroadcastReceiver，用于监听从Service传回来的广播
    public class ActivityReceiver extends BroadcastReceiver{
        public void onReceive(Context context,Intent intent){
            //update 代表播放状态
            int update = intent.getIntExtra("update",-1);
            //current代表正在播放的歌曲
            int current = intent.getIntExtra("current",-1);
            if(current>=0){
                title.setText(titleStrs[current]);
                title.setText(authorStrs[current]);
            }
            switch(update){
                case 0x11:
                    play.setImageResource(R.drawable.play);
                    status = 0x11;
                    break;
                case 0x12:
                    play.setImageResource(R.drawable.pause);
                    status = 0x12;//设置当前状态
                    break;
                case 0x13:
                    play.setImageResource(R.drawable.play);
                    status = 0x13;
                    break;
            }
        }
    }
    public void onClick(View source ){
        Intent intent = new Intent("cn.ssdut.lst.CTL_ACTION");
        switch(source.getId()){
            //按下播放/暂停按键
            case R.id.play:
                intent.putExtra("control",1);
                break;
            //按下停止按钮
            case R.id.stop:
                intent.putExtra("control",2);
                break;
            case R.id.prev:
                intent.putExtra("control",3);
                break;
            case R.id.next:
                intent.putExtra("control",4);
                break;
        }
        sendBroadcast(intent);
    }
}
