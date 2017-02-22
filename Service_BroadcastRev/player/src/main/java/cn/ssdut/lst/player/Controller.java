package cn.ssdut.lst.player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 * 一个音效控制器，没有layout文件，布局和视图都是在代码中定义的
 */
public class Controller extends AppCompatActivity {
    private MediaPlayer player;
    private Visualizer visualizer;//示波器
    private Equalizer equalizer;//均衡器
    private BassBoost bassBoost;//重低音增强器
    private PresetReverb presetReverb;//系统预设的音场控制器
    private LinearLayout layout;
    private List<Short> reverbNames = new ArrayList<>();
    private List<String> reverbVals = new ArrayList<>();
    private MusicService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取MusicService的onBind方法返回的Binder对象
            //Toast.makeText(getApplicationContext(), "ComponentName是："+name+"\nService Connected", Toast.LENGTH_SHORT).show();
            //从Service中得到player对象
            binder = (MusicService.MyBinder) service;
            player = binder.getPlayer();
            //对各种控制器进行初始化
            setupVisualizer();
            setupEqualizer();
            setupBassBoost();
            setupPresetReverb();

        }
        public void onServiceDisconnected(ComponentName name){
            //Toast.makeText(Controller.this, "ComponentName是："+name+"\nService Disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置控制音乐声音
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        //player = MediaPlayer.create(this, R.raw.byebyebye);
        //player.start();//开始播放音乐

        Intent intent = new Intent(this,MusicService.class);
        bindService(intent,conn,0);//本来想用Controller这个Service控制后台的MusicSerice的，结果conn的onServiceConnected貌似不能正常调用

    }
    private void setupVisualizer() {
        final MyVisualizerView mVisualizerView = new MyVisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, (int) (120f * getResources().getDisplayMetrics().density)));

        //将MyVisualizerView组件添加到layout容器中
        layout.addView(mVisualizerView);
        //以MediaPlayer的AudioSessionId创建Visualizer
        //相当于设置Visualizer负责显示该MediaPlayer的音频数据
        visualizer = new Visualizer(player.getAudioSessionId());
        visualizer.setCaptureSize(visualizer.getCaptureSizeRange()[1]);//0号元素是最小的captureSize,1号元素是最大的CaptureSize
        //设置监听器
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
            }

            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                //用waveform波形数据更新visualizerView组件
                mVisualizerView.updateVisualizer(waveform);
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
        visualizer.setEnabled(true);
        mVisualizerView.setEnabled(true);//让视图有效
    }

    private void setupEqualizer() {
        //以MediaPlayer的AudioSessionId创建Equalizer
        //相当于设置Equalizer负责控制该MediaPlayer
        equalizer = new Equalizer(0, player.getAudioSessionId());//优先级，AudioSessionId
        //启动均衡控制效果
        equalizer.setEnabled(true);
        TextView eqTitle = new TextView(this);
        eqTitle.setText("均衡器");
        layout.addView(eqTitle);
        //获取均衡器支持的最大值和最小值
        final short minEQLevel = equalizer.getBandLevelRange()[0];//最小值
        short maxEQLevel = equalizer.getBandLevelRange()[1];//最大值
        //获取均衡器支持的所有频率
        short brands = equalizer.getNumberOfBands();
        for (short i = 0; i < brands; i++) {
            //创建一个TextView ，用于显示频率
            TextView eqTextView = new TextView(this);
            eqTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            eqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            //设置该均衡控制器的频率
            eqTextView.setText((equalizer.getCenterFreq(i) / 1000) + "Hz");
            layout.addView(eqTextView);
            //创建一个水平排列的LinearLayout
            LinearLayout tmpLayout = new LinearLayout(this);
            tmpLayout.setOrientation(LinearLayout.HORIZONTAL);
            //创建显示均衡器最小值的TextView
            TextView minDbTextView = new TextView(this);
            minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //显示均衡控制器的最小值
            minDbTextView.setText((minEQLevel / 100) + "dB");
            //创建显示均衡器最大值的TextView
            TextView maxDbTextView = new TextView(this);
            maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            maxDbTextView.setText((maxEQLevel / 100) + "db");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            //定义seekBar作为调整工具
            SeekBar bar = new SeekBar(this);
            bar.setLayoutParams(layoutParams);
            bar.setMax(maxEQLevel - minEQLevel);
            bar.setProgress(equalizer.getBandLevel(i));
            final short brand = i;
            //为seekBar的拖动事件绑定监听器
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    equalizer.setBandLevel(brand, (short) (progress + minEQLevel));
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            //使用水平排列的LinearLayout排列3个组件
            tmpLayout.addView(minDbTextView);
            tmpLayout.addView(bar);
            tmpLayout.addView(maxDbTextView);
            //将水平排列的LinearLayout加入到整体的Layout中去
            layout.addView(tmpLayout);
        }
    }

    //初始化重低音控制器
    private void setupBassBoost() {
        //以MediaPlayer的AudioSession创建BassBoost
        //相当于设置BassBoost负责控制该MediaPlayer
        bassBoost = new BassBoost(0, player.getAudioSessionId());
        bassBoost.setEnabled(true);
        TextView bbTitle = new TextView(this);
        bbTitle.setText("重低音控制器");
        layout.addView(bbTitle);
        SeekBar bar = new SeekBar(this);
        //重低音范围0到1000
        bar.setMax(1000);
        bar.setProgress(0);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //设置重低音强度
                bassBoost.setStrength((short) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        layout.addView(bar);
    }

    //初始化音场控制器
    private void setupPresetReverb() {
        presetReverb = new PresetReverb(0,player.getAudioSessionId());
        TextView prTitle = new TextView(this);
        prTitle.setText("音场");
        layout.addView(prTitle);
        for(short i=0;i<equalizer.getNumberOfPresets();i++) {
            reverbNames.add(i);
            reverbVals.add(equalizer.getPresetName(i));
        }
        //使用Spinner作为音场选择工具
        Spinner sp = new Spinner(this);
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reverbVals));
        //为Spinner中的选项 设置监听器
        sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //设定音场
                presetReverb.setPreset(reverbNames.get(arg2));
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        layout.addView(sp);
    }

    protected void onPause() {
        super.onPause();
        if (isFinishing() && player != null) {
            //释放所有对象
            visualizer.release();
            equalizer.release();
            presetReverb.release();
            bassBoost.release();
            //这里的player和Service中的Player是一个对象
            //player.release();//如果这里把player释放掉的话，当返回MainActivity时，就不会继续播放歌曲了
            player=null;
        }
    }
    //MyVisualizerView视图类
    private static class MyVisualizerView extends View {
        //bytes数组保存了波形抽样点的值
        private byte[] bytes;
        private float[] points;
        private Paint paint = new Paint();
        private Rect rect = new Rect();
        private byte type = 0;

        public MyVisualizerView(Context context) {
            super(context);
            bytes = null;
            paint.setStrokeWidth(1f);
            paint.setAntiAlias(true);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
        }

        public void updateVisualizer(byte[] ftt) {
            bytes = ftt;
            invalidate();//通知该组件重新绘制自己
        }

        public boolean onTouchEvent(MotionEvent me) {
            //当用户触碰该组件时，切换波形类型
            if (me.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }
            type++;
            if (type >= 3) {
                type = 0;
            }
            return true;
            //return true表示已处理事件
            //return false表示没有处理该事件
        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (bytes == null) {
                //Toast.makeText(getContext(), "bytes为null", Toast.LENGTH_SHORT).show();
                return;
            }
            //绘制白色背景
            canvas.drawColor(Color.WHITE);
            rect.set(0, 0, getWidth(), getHeight());
            switch (type) {
                //-----------绘制块状的波形图---------------
                case 0:
                    for (int i = 0; i < bytes.length - 1; i++) {
                        float left = getWidth() * i / (bytes.length - 1);
                        //根据波形计算出矩形的高度(每个小矩形)
                        float top = rect.height() - (byte)(bytes[i + 1] + 128) * rect.height() / 128;//???
                        float right = left + 1;
                        float bottom = rect.height();
                        canvas.drawRect(left, top, right, bottom, paint);
                    }
                    break;
                //-----------绘制柱状的波形图,每隔18个抽样点绘制一个矩形---------------
                case 1:
                    for (int i = 0; i < bytes.length - 1; i += 18) {
                        float left = rect.width() * i / (bytes.length - 1);
                        float top = rect.height() - (byte) (bytes[i + 1] + 128) * rect.height() / 128;
                        float right = left + 6;
                        float bottom = rect.height();
                        canvas.drawRect(left, top, right, bottom, paint);
                    }
                    break;

                //-----------绘制曲线波形图---------------
                case 2:
                    //如果points数组还未初始化
                    if (points == null || points.length < bytes.length * 4) {
                        points = new float[bytes.length * 4];
                    }
                    for (int i = 0; i < bytes.length - 1; i++) {
                        //计算第i个点的x坐标
                        points[i * 4] = rect.width() * i / (bytes.length - 1);
                        //根据第bytes[i]个点的值(波形点的值)计算第i个点的y坐标
                        points[i * 4 + 1] = (rect.height() / 2) + ((byte) (bytes[i] + 128)) * 128 / (rect.height() / 2);
                        //计算第i+1个点的x坐标
                        points[i * 4 + 2] = rect.width() * (i + 1) / (bytes.length - 1);
                        //根据第bytes[i+1]个点的值(波形点的值)计算第i+1个点的y坐标
                        points[i * 4 + 3] = (rect.height() / 2) + ((byte) (bytes[i+1] + 128)) * 128 / (rect.height() / 2);
                    }
                    canvas.drawLines(points, paint);//绘制波形线
                    break;
            }
        }
    }

}
