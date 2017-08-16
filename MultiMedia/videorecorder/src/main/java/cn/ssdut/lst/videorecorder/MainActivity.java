package cn.ssdut.lst.videorecorder;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

/**
 * 疯狂Android讲义中的例子程序
 * 没有运行成功
 * 在56行报错，video source has already been set
 * 2016.12.14
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton record,stop;
    File videoFile;
    MediaRecorder mRecorder;
    SurfaceView sView;
    private boolean isRecording = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        record = (ImageButton) findViewById(R.id.record);
        stop = (ImageButton) findViewById(R.id.stop);
        //让stop按钮不可用
        stop.setEnabled(false);
        record.setOnClickListener(this);
        stop.setOnClickListener(this);
        sView = (SurfaceView) findViewById(R.id.sView);
        //设置SurfaceView不需要自己维护缓冲区
        sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //设置分辨率
        sView.getHolder().setFixedSize(320,280);
        //让屏幕不会自动关闭
        sView.getHolder().setKeepScreenOn(true);
    }
    public void onClick(View source){
        switch (source.getId()) {
            case R.id.record:
                if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(MainActivity.this, "sd卡不存在，请插入sd卡后重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    //创建保存录制视频的保存文件
                    videoFile = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/myVideo.mp4");
                    mRecorder = new MediaRecorder();
                    mRecorder.reset();
                    //设置从麦克风采集声音
                    mRecorder.setVideoSource(MediaRecorder.AudioSource.MIC);
                    //设置从摄像头采集图像
                    mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    //设置视频文件的输出格式
                    //必须在设置声音编码格式，图像编码格式之前设置
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    //设置音频编码格式
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //设置图像编码格式
                    mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                    mRecorder.setVideoSize(320, 280);
                    //每秒4帧
                    mRecorder.setVideoFrameRate(4);
                    mRecorder.setOutputFile(videoFile.getAbsolutePath());
                    Log.d("tag","videoFile文件的绝对路径是："+videoFile.getAbsolutePath());
                    //指定使用SurfaceView来预览视频
                    mRecorder.prepare();
                    //开始录制
                    mRecorder.start();
                    Log.d("tag", "正在录制");
                    //将录制按钮设为不可用
                    record.setEnabled(false);
                    stop.setEnabled(true);
                    isRecording = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.stop:
                if (isRecording) {
                    mRecorder.stop();
                    mRecorder.release();//释放资源，放开摄像头啊，它是无辜的啊，干嘛老抓着人家不放
                    mRecorder=null;
                    record.setEnabled(true);
                    stop.setEnabled(false);
                }
                break;
        }

    }
}
