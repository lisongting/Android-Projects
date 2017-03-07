package cn.ssdut.lst.metalslug;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    //定义逐布局内的容器FrameLayout
    public static FrameLayout mainLayout = null;
    //主布局的布局参数
    public static FrameLayout.LayoutParams mainLP = null;
    //定义资源管理的核心类
    public static Resources res = null;
    public static MainActivity mainActivity = null;
    //定义成员变量记录游戏窗口的宽度和高度
    public static int windowWidth;
    public static int windowHeight;
    //游戏窗口的主游戏界面
    public static GameView mainView = null;
    //播放背景音乐的MediaPlayer
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metric = new DisplayMetrics();
        //获取屏幕的高度，宽度
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        windowHeight = metric.heightPixels;//屏幕高度
        windowWidth = metric.widthPixels;//屏幕宽度
        Log.i("tag", "MainActivity中，屏幕高度:" + windowHeight + " 屏幕宽度:" + windowWidth);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        res = getResources();
        setContentView(R.layout.activity_main);
        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        //创建GameView
        mainView = new GameView(this.getApplicationContext(), GameView.STAGE_INIT);

        mainLP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayout.addView(mainView, mainLP);
        //播放背景音乐
        player = MediaPlayer.create(this, R.raw.background);
        player.setLooping(true);
        player.start();
    }

    public void onPause() {
        super.onPause();
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }
    public void onResume() {
        super.onResume();
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }
}
