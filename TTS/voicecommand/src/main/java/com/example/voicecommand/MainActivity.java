package com.example.voicecommand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    public static final String GRAMMAR = "#ABNF 1.0 UTF-8;\n" +
            "language zh-CN;\n" +
            "mode voice;\n" +
            "root command;\n" +
            "$command = $action [$speech];\n" +
            "$action = 停止|继续|恢复|开始;\n" +
            "$speech = 解说|播放;";
    private Button button;
    private SpeechRecognizer recognizer;
    private RecognizerListener recognizerListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
//            }
//        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                    recognizer.startListening(recognizerListener);


                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Toast.makeText(MainActivity.this, "录音结束", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        initSpeechRecognizer();


    }

    private void initSpeechRecognizer() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59198461");

        recognizer = SpeechRecognizer.createRecognizer(this, new InitListener() {
            @Override
            public void onInit(int status) {
                if (status == ErrorCode.SUCCESS) {
                    Log.i(TAG, "SpeechRecognizer初始化成功");
                } else {
                    Log.i(TAG, "SpeechRecognizer初始化失败，错误码："+status);
                }
            }
        });

        recognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        recognizer.buildGrammar("abnf", GRAMMAR, new GrammarListener() {
            @Override
            public void onBuildFinish(String grammarId, SpeechError speechError) {
                Log.i(TAG, "grammarId:" + grammarId);
                if (speechError == null) {
                    Log.i(TAG, "语法构建成功");
                } else {
                    Log.i(TAG, "speechError code:" + speechError.getErrorCode() + ",message" + speechError.getMessage());
                }
            }
        });

        recognizerListener = new RecognizerListener() {
            @Override
            public void onVolumeChanged(int volume, byte[] data) {
                Log.i(TAG, "RecognizerListener -- onVolumeChanged()");
            }

            @Override
            public void onBeginOfSpeech() {
                Log.i(TAG, "RecognizerListener -- onBeginOfSpeech()");

            }

            @Override
            public void onEndOfSpeech() {
                Log.i(TAG, "RecognizerListener -- onEndOfSpeech()");

            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                Log.i(TAG, "RecognizerListener -- onResult()");
                Log.i(TAG, "isLast:" + isLast);
                Log.i(TAG, "recognizerResult:" + recognizerResult.getResultString());
                Toast.makeText(MainActivity.this, "isLast:"+isLast+",recognizerResult:"+recognizerResult.getResultString()
                        , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(SpeechError speechError) {
                Log.i(TAG, "RecognizerListener -- onError():"+speechError.getErrorCode()
                        +","+speechError.getErrorDescription());

            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle bundle) {
                Log.i(TAG, "RecognizerListener -- onEvent()");

            }
        };


    }

    @Override
    public void onDestroy() {
        if (recognizer!= null) {
            recognizer.cancel();
            recognizer.destroy();
        }

        super.onDestroy();
    }
}
