package cn.ssdut.lst.multimedia;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明化，这样就可以调用setAlpha,drawColor等函数
        setContentView(R.layout.activity_main);
        VideoView videoView = (VideoView) findViewById(R.id.video);
        MediaController controller = new MediaController(this);
        File video = new File("/mnt/sdcard/DCIM/Video/test.mp4");
        if (video.exists()) {
            videoView.setVideoPath(video.getAbsolutePath());
            videoView.setMediaController(controller);
            controller.setMediaPlayer(videoView);
            //让VideoView获得焦点
            videoView.requestFocus();
        }else{
            Toast.makeText(MainActivity.this, "Video不存在", Toast.LENGTH_SHORT).show();
        }

    }
}
