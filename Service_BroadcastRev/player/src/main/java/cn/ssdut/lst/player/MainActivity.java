package cn.ssdut.lst.player;

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

    public static final int STATUS_NONE=11;//没有播放
    public static final int STATUS_PLAY=22;//播放
    public static final int STATUS_PAUSE=33;//暂停
    public static final int CONTROL_NEXT=111;
    public static final int CONTROL_PREV=222;
    public static final int CONTROL_PLAYorPAUSE=333;
    public static final int CONTROL_STOP=444;
    TextView title ,singer;
    ImageButton play,stop,prev,next;
    public static final String CTL_ACTION="player.action.CTL_ACTION";//控制Service播放的action,被Service的broadcastReceiver接受
    public static final String UPDATE_ACTION="player.action.CTL_UPDATE";//控制Mainactivity界面更新的action,被MainActivity的broadcastReceiver接受
    String[] titleStrs={"Free Loop","Little Flower","You are Beautiful"};
    String[] authorStrs = {"Daniel Powter","Devize","James Blunt"};
    ActivityReceiver activityRecerver;
    int status = STATUS_NONE;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView)findViewById(R.id.title);
        singer = (TextView)findViewById(R.id.singer);
        play = (ImageButton)findViewById(R.id.bt_play);play.setOnClickListener(this);
        stop = (ImageButton)findViewById(R.id.bt_stop);stop.setOnClickListener(this);
        prev = (ImageButton)findViewById(R.id.bt_prev);prev.setOnClickListener(this);
        next = (ImageButton)findViewById(R.id.bt_next);next.setOnClickListener(this);
        activityRecerver = new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);//为UI界面的Receiver添加一个过滤器,Action设置为UPDATE_ACTION
        //注册BroadcastReceiver
        registerReceiver(activityRecerver,filter);
        intent = new Intent(this,MusicService.class);
        startService(intent);//启动播放音乐的Service
    }

    public class ActivityReceiver extends BroadcastReceiver {
        public void onReceive(Context context,Intent intent){
            int playStatus = intent.getIntExtra("playStatus",-1);//获得Service播放歌曲的状态。默认-1
            int currentSong = intent.getIntExtra("currentSong",-1);//获得正在播放的歌曲
            if (currentSong >= 0) {//在上面显示歌曲名称和歌手
                title.setText(titleStrs[currentSong]);
                singer.setText(authorStrs[currentSong]);
            }
            switch(playStatus){
                case STATUS_NONE://如果没有歌曲播放
                    play.setImageResource(R.drawable.play);
                    status = STATUS_NONE;
                    break;
                case STATUS_PAUSE://如果是在暂停
                    play.setImageResource(R.drawable.play);
                    status = STATUS_PAUSE;
                    break;
                case STATUS_PLAY:
                    play.setImageResource(R.drawable.pause);
                    status = STATUS_PLAY;
                    break;
                default:
                    break;
            }
        }
    }
    public void onClick(View v){
        Intent intent = new Intent(CTL_ACTION);
        switch(v.getId()){
            case R.id.bt_next:
                intent.putExtra("control",CONTROL_NEXT);
                break;
            case R.id.bt_play://控制播放/暂停的按钮
                intent.putExtra("control",CONTROL_PLAYorPAUSE);//传入控制指令，若Service在播放，则进行暂停，若后台暂停，则进行播放
                break;
            case R.id.bt_prev:
                intent.putExtra("control",CONTROL_PREV);
                break;
            case R.id.bt_stop:
                intent.putExtra("control", CONTROL_STOP);
                break;
        }
        sendBroadcast(intent);//把广播发出去，让Service听到

    }
    public void openController(View source){
        Intent intent = new Intent(MainActivity.this,Controller.class);
        startActivity(intent);
    }
    protected void onDestroy(){
        stopService(intent);
        super.onDestroy();
    }
}
