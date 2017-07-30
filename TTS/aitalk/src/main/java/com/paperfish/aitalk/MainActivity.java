package com.paperfish.aitalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import org.json.JSONObject;

/**
 * 测试使用讯飞的AI对话功能
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "aiui";
    private EditText editText;
    private TextView info;
    private Button startVoiceTalk;
    private AIUIAgent agent;

    private AIUIParams params;
    private String strParams;
    private SpeechUnderstander speechUnderstander;
    private AIUIListener aiuiListener;

    private SpeechSynthesizer ttsSynthesizer;
    private SynthesizerListener synthesizerListener;

    private int mAIUIState = AIUIConstant.STATE_IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.tv_info);
        editText = (EditText) findViewById(R.id.editText);
        startVoiceTalk = (Button) findViewById(R.id.voiceUnderstand);
        Log.i(TAG, "MainActivity -- onCreate()");
        startVoiceTalk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Log.i(TAG, "MotionEvent.ACTION_DOWN");
                    startVoiceTalk();
                }
                if (action == MotionEvent.ACTION_UP) {
                    Log.i(TAG, "MotionEvent.ACTION_UP");
//                    stopVoiceTalk();
                }
                return false;
            }
        });
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59198461");
    }

    @Override
    protected void onResume() {
        super.onResume();
        strParams = createParams();
        Log.i(TAG, strParams);
        Log.i(TAG, "MainActivity -- onResume()");
        //创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener，可以为Null
        ttsSynthesizer = SpeechSynthesizer.createSynthesizer(this, null);

        //xiaoyan：青年女声-普通话  xiaoyu：青年男声-普通话   vixx：小男孩-普通话    vinn：小女孩-普通话
        ttsSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "vinn"); //设置发音人
        ttsSynthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        ttsSynthesizer.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        ttsSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端

        aiuiListener = new AIUIListener() {
            @Override
            public void onEvent(AIUIEvent event) {
                switch (event.eventType) {
                    case AIUIConstant.EVENT_WAKEUP:
                        Log.i( TAG,  "on event: "+ event.eventType );
//                        showTip( "进入识别状态" );
                        break;

                    case AIUIConstant.EVENT_RESULT: {
                        Log.i( TAG,  "on event: "+ event.eventType );
                        try {
                            JSONObject bizParamJson = new JSONObject(event.info);
                            JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                            JSONObject params = data.getJSONObject("params");
                            JSONObject content = data.getJSONArray("content").getJSONObject(0);

                            if (content.has("cnt_id")) {
                                String cnt_id = content.getString("cnt_id");
                                JSONObject cntJson = new JSONObject(new String(event.data.getByteArray(cnt_id), "utf-8"));

                                AIMessage aiMessage;
                                Gson gson = new Gson();
                                aiMessage = gson.fromJson(cntJson.toString(), AIMessage.class);
                                AIMessage.IntentBean.AnswerBean answerBean = aiMessage.getIntent().getAnswer();
                                String str = "";
                                str = answerBean.getText();
//                                if (anserBean != null) {
//                                    str = anserBean.getText();
//                                } else {
//                                    str = "这个问题太难了，换一个吧";
//                                }
                                info.setText(str);
                                speakOutResult(str);
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                            info.append( "\n" );
                            info.append( e.getLocalizedMessage() );
                        }

                        info.append( "\n" );
                    } break;

                    case AIUIConstant.EVENT_ERROR: {
                        Log.i( TAG,  "on event: "+ event.eventType );
                        info.append( "\n" );
                        info.append( "错误: "+event.arg1+"\n"+event.info );
                    } break;

                    case AIUIConstant.EVENT_START_RECORD: {
                        Log.i( TAG,  "on event: "+ event.eventType );
                        showTip("开始录音");
                    } break;

                    case AIUIConstant.EVENT_STOP_RECORD: {
                        Log.i( TAG,  "on event: "+ event.eventType );
                        showTip("停止录音");
                    } break;

                    case AIUIConstant.EVENT_STATE: {	// 状态事件
                        mAIUIState = event.arg1;

                        if (AIUIConstant.STATE_IDLE == mAIUIState) {
                            // 闲置状态，AIUI未开启
                            showTip("STATE_IDLE");
                        } else if (AIUIConstant.STATE_READY == mAIUIState) {
                            // AIUI已就绪，等待唤醒
                            showTip("STATE_READY");
                        } else if (AIUIConstant.STATE_WORKING == mAIUIState) {
                            // AIUI工作中，可进行交互
                            showTip("STATE_WORKING");
                        }
                    } break;

                    case AIUIConstant.EVENT_CMD_RETURN:{
                        if( AIUIConstant.CMD_UPLOAD_LEXICON == event.arg1 ){
                            showTip( "上传"+ (0==event.arg2?"成功":"失败") );
                        }
                    }break;

                    default:
                        break;
                }
            }
        };

        synthesizerListener = new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                //TODO:在播放完成后，开始下一轮对话，录音
                startVoiceTalk();

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
        agent = AIUIAgent.createAgent(getApplicationContext(), strParams, aiuiListener);

//        创建AIUIAgent对象
//        AIUIEvent中的arg1参数：
//        0   STATE_IDLE （空闲状态）
//        1   STATE_READY （就绪状态，待唤醒）
//        2   STATE_WORKING（工作状态，已唤醒）
//        agent = AIUIAgent.createAgent(getApplicationContext(),strParams,new AIUIListener(){
//            @Override
//            public void onEvent(AIUIEvent aiuiEvent) {
//                Log.i(TAG, "eventType:" + aiuiEvent.eventType);
//                Log.i(TAG, "info:" + aiuiEvent.info);
//                Log.i(TAG, "arg1:" + aiuiEvent.arg1 + " arg2:" + aiuiEvent.arg2);
//
//                if (aiuiEvent.data != null) {
//                    Bundle bundle = aiuiEvent.data;
//                    for (String key : bundle.keySet()) {
//                        Log.i(TAG, "key:" + key + "value:" + bundle.getString(key));
//                    }
//                }
//                if (aiuiEvent.info != null) {
//                    JSONObject bizParamJson;
//                    try {
//                        bizParamJson = new JSONObject(aiuiEvent.info);
//                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
//                        JSONObject params = data.getJSONObject("params");
//                        JSONObject content = data.getJSONArray("content").getJSONObject(0);
//                        if (content.has("cnt_id")) {
//                            String cnt_id = content.getString("cnt_id");
//                            JSONObject cntJson = new JSONObject(new String(aiuiEvent.data.getByteArray(cnt_id), "utf-8"));
//                            Log.i(TAG, cntJson.toString());
//
//                            AIMessage aiMessage;
//                            Gson gson = new Gson();
//                            aiMessage = gson.fromJson(cntJson.toString(), AIMessage.class);
//                            String str = aiMessage.getIntent().getAnswer().getText();
//                            info.setText(str);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

    }

    private void showTip(String str) {
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
    }

    private String createParams() {
        return "{\"interact\":{\"interact_timeout\":\"60000\",\"result_timeout\":\"5000\"}," +
                "\"global\":{\"scene\":\"main\",\"clean_dialog_history\":\"auto\"}," +
                "\"vad\":{\"vad_enable\":\"1\",\"engine_type\":\"meta\",\"res_type\":\"assets\",\"res_path\":\"vad/meta_vad_16k.jet\"}," +
                "\"iat\":{\"sample_rate\":\"16000\"}," +
                "\"speech\":{\"data_source\":\"sdk\"}}";
    }

    public void startTextUnderstand(View view) {
        //第一种方式：使用AIUIAgent的方式来获取AI的回答内容
        //问题在于：服务器返回的字段和官网上描述的不一样
        String text = editText.getText().toString().trim();
        if (text.length() > 0) {
            byte[] data = text.getBytes();
            String param = "data_type=text";
            AIUIMessage message = new AIUIMessage(AIUIConstant.CMD_WRITE, 0, 0, param, data);
            agent.sendMessage(message);
            Log.i(TAG, "send text understand message");
        }
    }

    public void startVoiceTalk() {
        Log.i(TAG, "start AI talk");
        //唤醒AIUIAgent
        AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
        agent.sendMessage(wakeupMsg);

        //开始录音
        String param = "sample_rate=16000,data_type=audio";
        AIUIMessage audioMessage = new AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0, param, null);
        agent.sendMessage(audioMessage);

    }

    //将从服务器返回的AI对话文本结果转换为语音播放出来
    public void speakOutResult(String str) {
        ttsSynthesizer.startSpeaking(str,synthesizerListener );

    }
    private void stopVoiceTalk(){
        Log.i( TAG, "stop AI talk" );
        // 停止录音
        String params = "sample_rate=16000,data_type=audio";
        AIUIMessage stopWriteMsg = new AIUIMessage(AIUIConstant.CMD_STOP_RECORD, 0, 0, params, null);

        agent.sendMessage(stopWriteMsg);
    }

    @Override
    protected void onDestroy() {
        agent.destroy();
        ttsSynthesizer.stopSpeaking();
        ttsSynthesizer.destroy();

        super.onDestroy();
    }
}
