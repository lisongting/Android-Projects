package cn.ssdut.lst.screen_catcher;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_CODE=123;
    private MediaProjectionManager mediaProjectionManager;
    private int screenDensity;
    private int displayWidth=360;
    private int displayHeight=640;
    private boolean screenSharing;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private SurfaceView surfaceView;
    private Surface surface;//处理原始图像信息的一个类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕分辨率
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.densityDpi;
        //获取应用界面上的SurfaceView组件
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surface = surfaceView.getHolder().getSurface();
        //获取SurfaceView的宽度和高度
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.height=displayHeight;
        lp.width = displayWidth;
        surfaceView.setLayoutParams(lp);
        //获取MediaProjectionManager管理器
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

    }
    @TargetApi(21)//通过这个注释才能调用stop方法
    public void onDestroy(){
        super.onDestroy();
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection =null;
        }
    }

}
