package com.example.xunfeitts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private  SpeechSynthesizer mTts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.id_edittext);

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59198461");

        Log.i("tag", "ExternalCacheDir" + getExternalCacheDir());

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private SynthesizerListener mSynListener = new SynthesizerListener(){
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            Log.i("tag", "----onCompleted----");
        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            Log.i("tag", "----onBufferProgress----");
        }
        //开始播放
        public void onSpeakBegin() {
            Log.i("tag", "----onSpeakBegin----");
        }
        //暂停播放
        public void onSpeakPaused() {
            Log.i("tag", "----onSpeakPaused----");
        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            Log.i("tag", "----onSpeakProgress----");
        }
        //恢复播放回调接口
        public void onSpeakResumed() {
            Log.i("tag", "----onSpeakResumed----");
        }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            Log.i("tag", "----onEvent----");
        }
    };

    public void startReading(View v) {
        String text = editText.getText().toString();
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        mTts= SpeechSynthesizer.createSynthesizer(this, null);

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码

        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, getExternalCacheDir()+"/iflytek.pcm");

        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
        //合成监听器
    }

    public void stopReading(View v) {
        mTts.stopSpeaking();
    }
}
