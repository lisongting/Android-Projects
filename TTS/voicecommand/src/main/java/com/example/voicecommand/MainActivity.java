package com.example.voicecommand;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    public static final String GRAMMAR = "#ABNF 1.0 UTF-8;\n" +
            "language zh-CN;\n" +
            "mode voice;\n" +
            "root command;\n" +
            "$command = $action [$speech];\n" +
            "$action = 暂停|停止|继续|恢复|开始;\n" +
            "$speech = 解说|播放;";
    private Button button;
    private SpeechRecognizer recognizer;
    private RecognizerListener recognizerListener;
    private FloatingActionButton floatingActionButton;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_bar);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
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

        floatingActionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                    recognizer.startListening(recognizerListener);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Toast.makeText(MainActivity.this, "录音结束", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        initSpeechRecognizer();

        final Snackbar snackbar = Snackbar.make(coordinatorLayout,
                "你可以按住上方的按钮说出语音指令词，" +
                        "如：停止播放/解说、开始播放/解说等"
                ,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        TextView textView = ((TextView) snackbar.getView().findViewById(R.id.snackbar_text));
        snackbar.getView().setBackgroundColor(Color.parseColor("#8DEEEE"));
        textView.setTextColor(Color.parseColor("#303F9F"));
        snackbar.show();



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
//                Log.i(TAG, "RecognizerListener -- onVolumeChanged()");
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
//                Log.i(TAG, "RecognizerListener -- onResult()");
//                Log.i(TAG, "isLast:" + isLast);
//                Log.i(TAG, "recognizerResult:" + recognizerResult.getResultString());
//                Toast.makeText(MainActivity.this, "isLast:"+isLast+",recognizerResult:"+recognizerResult.getResultString()
//                        , Toast.LENGTH_LONG).show();
                if (!isLast) {
                    handleRecognizerResult(recognizerResult);
                }


            }

            @Override
            public void onError(SpeechError speechError) {
                Log.i(TAG, "RecognizerListener -- onError():["+speechError.getErrorCode()
                        +"],"+speechError.getErrorDescription());
                Toast.makeText(MainActivity.this, speechError.getErrorDescription(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle bundle) {
                Log.i(TAG, "RecognizerListener -- onEvent()");

            }
        };


    }

    private void handleRecognizerResult(RecognizerResult recognizerResult) {
        String result = recognizerResult.getResultString();

        Gson gson = new Gson();
        CommandRecogResult resultEntity = gson.fromJson(result, CommandRecogResult.class);
        Log.i(TAG, resultEntity.toString());

        List<CommandRecogResult.WsBean> list = resultEntity.getWs();
        if (list.size() == 1) {
            List<CommandRecogResult.WsBean.CwBean> cwBeanList = list.get(0).getCw();
            String word = cwBeanList.get(0).getW();
            if (word.equals("开始") || word.equals("恢复") || word.equals("继续")) {
                Log.i(TAG,"第一个指令正确：" + word);
//                mAudioManager.resume();
            }else if ( word.equals("暂停")||word.equals("停止")) {
                Log.i(TAG,"第一个指令正确：" + word);
//                mAudioManager.pause();
            }else{
                Log.i(TAG, "指令识别失败：" + result);
            }
            Toast.makeText(this, word, Toast.LENGTH_SHORT).show();
        } else if (list.size() == 2) {
            List<CommandRecogResult.WsBean.CwBean> cwBeanList1 = list.get(0).getCw();
            String word1 = cwBeanList1.get(0).getW();

            if (word1.equals("开始") || word1.equals("恢复") || word1.equals("继续")) {
                Log.i(TAG,"第一个指令正确：" + word1);
                List<CommandRecogResult.WsBean.CwBean> cwBeanList2 = list.get(1).getCw();
                String word2 = cwBeanList2.get(0).getW();
                if (word2.equals("播放")||word2.equals("解说") ) {
                    Log.i(TAG,"第二个指令正确：" + word2);
                    Toast.makeText(this, word1+word2, Toast.LENGTH_SHORT).show();
//                    mAudioManager.resume();
                }
            } else if (word1.equals("暂停") || word1.equals("停止")) {
                Log.i(TAG, "第一个指令正确：" + word1);
                List<CommandRecogResult.WsBean.CwBean> cwBeanList2 = list.get(1).getCw();
                String word2 = cwBeanList2.get(0).getW();
                if (word2.equals("播放") || word2.equals("解说")) {
                    Log.i(TAG, "第二个指令正确：" + word2);
                    Toast.makeText(this, word1+word2, Toast.LENGTH_SHORT).show();
//                    mAudioManager.pause();
                }
            } else {
                Log.i(TAG, "指令识别失败：" + result);
            }
        } else {
            Log.i(TAG, "指令识别失败：" + result);
            Toast.makeText(this, "请说出正确的口令词", Toast.LENGTH_SHORT).show();
        }
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
