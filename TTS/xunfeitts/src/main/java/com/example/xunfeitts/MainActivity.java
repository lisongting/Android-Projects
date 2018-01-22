package com.example.xunfeitts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private  SpeechSynthesizer mTts;
    private SynthesizerListener mSynListener;
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

        mSynListener = new SynthesizerListener(){
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

        mTts= SpeechSynthesizer.createSynthesizer(this, null);

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码

        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, getExternalCacheDir()+"/iflytek.pcm");

    }


    public void startReading(View v) {
        String text = editText.getText().toString();
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        if (mTts == null) {
            mTts= SpeechSynthesizer.createSynthesizer(this, null);

            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
            mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
            mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        }

        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
        //合成监听器
    }

    public void stopReading(View v) {
        mTts.stopSpeaking();
    }

    public void clickButton(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();
        mTts.startSpeaking(text, mSynListener);
    }

    private String makeStringToHex(String str) {
        byte[] src = new byte[0];
        try {
            src = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private boolean isFileExist(String filename) {
        File file = new File(getExternalCacheDir(),filename);
        return file.exists();
    }
}
