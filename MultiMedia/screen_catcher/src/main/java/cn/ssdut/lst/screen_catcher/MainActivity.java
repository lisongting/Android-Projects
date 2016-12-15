package cn.ssdut.lst.screen_catcher;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * 先提示一堆permission denied,然后报错：
 * seding message to a Handler on a dead thread
 * 不知是否是 手机分辨率不匹配的问题
 * 2016.12.14
 */

@TargetApi(21)
public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_CODE=123;
    private MediaProjectionManager projectionManager;
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
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

    }
    @TargetApi(21)//通过这个注释才能调用stop方法
    public void onDestroy(){
        super.onDestroy();
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection =null;
        }
    }

    //当用户单击开关按钮时激发该方法
    public void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
            shareScreen();
        }else{
            stopScreenSharing();
        }
    }

    private void shareScreen() {
        screenSharing = true;
        if (surface == null) {
            return ;
        }
        if (mediaProjection == null) {
            Intent intent = projectionManager.createScreenCaptureIntent();
            //启动该intent进行屏幕捕捉
            startActivityForResult(intent,CAPTURE_CODE);
            return ;
        }
    }
    private void stopScreenSharing(){
        screenSharing = false;
        if (virtualDisplay == null) {
            return ;
        }
        virtualDisplay.release();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CAPTURE_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(MainActivity.this, "用户取消了屏幕捕捉", Toast.LENGTH_SHORT).show();
                return;
            }
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            virtualDisplay = mediaProjection.createVirtualDisplay("屏幕捕捉", displayWidth, displayHeight, screenDensity
                    , DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, surface, null, null);
        }
    }
}
