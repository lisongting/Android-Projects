package cn.ssdut.lst.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //status用来描述Service中的播放器状态
    public final int STATUS_NONE = 11;//没有播放
    public final int STATUS_PLAY = 22;//正在播放
    public final int STATUS_PAUSE =33;//暂停状态
    //control是播放控制
    public final int CONTROL_PREV = 111;//上一首
    public final int CONTROL_NEXT = 222;
    public final int CONTROL_PLAYorPAUSE = 333;
    public final int CONTROL_STOP = 444;
    public final int CONTROL_SPEED = 555;//快进
    public final int CONTROL_REWIND =666;//快退
    public final String UPDATE_UI = "cn.lst.action.update_ui";
    public final String CTRL_PLAYER = "cn.lst.action.ctrl.player";
    private int currentStatus = STATUS_NONE;//初始状态
    private int currentSong = 0;
    private int songDuration;
    private TextView tv_song,tv_singer,tv_time;
    private SeekBar seekBar;
    private ImageButton bt_rewind,bt_prev,bt_play,bt_stop,bt_next,bt_speed;
    private String[] songs=new String[]{"Little Flower","Tomorrow","Home","Energy Drink"};
    private String[] singers=new String[]{"Devize","Julie","Michael Buble","Virtual Riot"};
    private ActivityReceiver activityReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_singer = (TextView) findViewById(R.id.tv_singer);
        tv_song = (TextView) findViewById(R.id.tv_song);
        tv_time = (TextView) findViewById(R.id.tv_time);
        seekBar = (SeekBar) findViewById(R.id.sb_seekBar);
        bt_play = (ImageButton) findViewById(R.id.bt_play);
        bt_prev = (ImageButton) findViewById(R.id.bt_prev);
        bt_rewind = (ImageButton) findViewById(R.id.bt_rewind);
        bt_stop = (ImageButton) findViewById(R.id.bt_stop);
        bt_next = (ImageButton) findViewById(R.id.bt_next);
        bt_speed = (ImageButton) findViewById(R.id.bt_speed);
        bt_play.setOnClickListener(this);
        bt_prev.setOnClickListener(this);
        bt_rewind.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_speed.setOnClickListener(this);
        activityReceiver = new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_UI);
        registerReceiver(activityReceiver, filter);
        //启动播放音乐的服务
        Intent playIntent = new Intent(this, MusicService.class);
        startService(playIntent);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //如果是用户拖动进度条，则通知Service中的player更改进度
                if (fromUser) {
                    Intent tmp = new Intent(CTRL_PLAYER);
                    tmp.putExtra("progressChanged", progress);
                    sendBroadcast(tmp);
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("tag", "onStartTrackingTouch..");
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("tag", "onStopTrackingTouch");
            }
        });
    }

    class ActivityReceiver extends BroadcastReceiver {
        public void onReceive(Context context,Intent intent) {
            currentStatus = intent.getIntExtra("currentStatus", -1);
            currentSong = intent.getIntExtra("currentSong", -1);
            seekBar.setMax(songDuration);
            int pos = intent.getIntExtra("pos", -1);//秒
            //显示歌曲和歌手
            if (currentSong >= 0) {
                tv_singer.setText("歌手："+singers[currentSong]);
                tv_song.setText("歌曲："+songs[currentSong]);
                songDuration = intent.getIntExtra("duration", -1);
            }
            //判断Service中player的播放状态
            switch (currentStatus) {
                case STATUS_NONE:
                    bt_play.setImageResource(R.drawable.play);
                    currentStatus = STATUS_NONE;
                    break;
                case STATUS_PLAY:
                    bt_play.setImageResource(R.drawable.pause);
                    currentStatus = STATUS_PLAY;
                    break;
                case STATUS_PAUSE:
                    bt_play.setImageResource(R.drawable.play);
                    currentStatus = STATUS_PAUSE;
                    break;
            }
            //更新进度条
            if (pos > 0&&songDuration>0) {
                seekBar.setProgress(pos);
                //用来显示当前播放进度与总进度
                int curMinute = pos/60;
                int curSecond = pos%60;
                int tolMinute = songDuration/60;
                int tolSecond = songDuration%60;
                tv_time.setText("0"+curMinute+":"+curSecond+"/0"+tolMinute+":"+tolSecond);
            }
        }
    }

    public void onClick(View view) {
        Intent t = new Intent(CTRL_PLAYER);
        switch (view.getId()) {
            case R.id.bt_rewind:
                t.putExtra("control", CONTROL_REWIND);
                break;
            case R.id.bt_prev:
                t.putExtra("control", CONTROL_PREV);
                break;
            case R.id.bt_play:
                t.putExtra("control", CONTROL_PLAYorPAUSE);
                break;
            case R.id.bt_stop:
                t.putExtra("control", CONTROL_STOP);
                break;
            case R.id.bt_next:
                t.putExtra("control", CONTROL_NEXT);
                break;
            case R.id.bt_speed:
                t.putExtra("control", CONTROL_SPEED);
                break;
            default:
                break;
        }
        //发送广播给MusicService，控制MediaPlayer的播放
        sendBroadcast(t);
    }
}
