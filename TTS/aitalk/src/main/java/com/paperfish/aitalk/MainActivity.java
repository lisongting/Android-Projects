package com.paperfish.aitalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUtility;

import org.json.JSONObject;

/**
 * 测试使用讯飞的AI对话功能
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "aiui";
    private EditText editText;
    private TextView info;
    private AIUIAgent agent;

    private AIUIParams params;
    private String strParams;
    private SpeechUnderstander speechUnderstander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.tv_info);
        editText = (EditText) findViewById(R.id.editText);

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59198461");
    }

    @Override
    protected void onResume() {
        super.onResume();
        strParams = createParams();
        Log.i(TAG, strParams);

//        创建AIUIAgent对象
//        AIUIEvent中的arg1参数：
//        0   STATE_IDLE （空闲状态）
//        1   STATE_READY （就绪状态，待唤醒）
//        2   STATE_WORKING（工作状态，已唤醒）
        agent = AIUIAgent.createAgent(getApplicationContext(),strParams,new AIUIListener(){
            @Override
            public void onEvent(AIUIEvent aiuiEvent) {
                Log.i(TAG, "eventType:" + aiuiEvent.eventType);
                Log.i(TAG, "info:" + aiuiEvent.info);
                Log.i(TAG, "arg1:" + aiuiEvent.arg1 + " arg2:" + aiuiEvent.arg2);

                if (aiuiEvent.data != null) {
                    Bundle bundle = aiuiEvent.data;
                    for (String key : bundle.keySet()) {
                        Log.i(TAG, "key:" + key + "value:" + bundle.getString(key));
                    }
                }
                if (aiuiEvent.info != null) {
//                    info.setText(aiuiEvent.info);

                    JSONObject bizParamJson;
                    try {
                        bizParamJson = new JSONObject(aiuiEvent.info);
                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                        JSONObject params = data.getJSONObject("params");
                        JSONObject content = data.getJSONArray("content").getJSONObject(0);
                        if (content.has("cnt_id")) {
                            String cnt_id = content.getString("cnt_id");
                            JSONObject cntJson = new JSONObject(new String(aiuiEvent.data.getByteArray(cnt_id), "utf-8"));
                            Log.i(TAG, cntJson.toString());

                            AIMessage aiMessage;
                            Gson gson = new Gson();
                            aiMessage = gson.fromJson(cntJson.toString(), AIMessage.class);
                            String str = aiMessage.getIntent().getAnswer().getText();
                            info.setText(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }

    private String createParams() {
        return "{\"interact\":{\"interact_timeout\":\"60000\",\"result_timeout\":\"5000\"}," +
                "\"global\":{\"scene\":\"main\",\"clean_dialog_history\":\"auto\"}," +
                "\"vad\":{\"vad_enable\":\"1\",\"engine_type\":\"meta\",\"res_type\":\"assets\",\"res_path\":\"vad/meta_vad_16k.jet\"}," +
                "\"iat\":{\"sample_rate\":\"16000\"}," +
                "\"speech\":{\"data_source\":\"user\"}}";

//        params = new AIUIParams();
//        //交互超时：60秒，结果等待超时：5秒
//        params.setInteract(new AIUIParams.InteractBean("60000","5000"));
//        params.setGlobal(new AIUIParams.GlobalBean("main","auto"));
//        params.setVad(new AIUIParams.VadBean("1", "meta", "assets", "res/vad/meta_vad_16k.jet"));
//        params.setIat(new AIUIParams.IatBean("16000"));
//        params.setSpeech(new AIUIParams.SpeechBean("sdk"));
//
//        Log.i(TAG, params.toString());
//        Gson gson = new Gson();
//        String json = gson.toString(params);

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

        //第二种方式：使用SpeechUnderstander的方式来获取AI的回答内容，但是不能进行文本应答的
//        speechUnderstander  = SpeechUnderstander.createUnderstander(this, new InitListener() {
//            @Override
//            public void onInit(int i) {
//                if (i == 0) {
//                    Log.i(TAG, "SpeechUnderstander初始化成功");
//                } else {
//                    Log.i(TAG, "init SpeechUnderstander result code：" + i);
//                }
//            }
//        });
//        speechUnderstander.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//        byte[] data = text.getBytes();
//        speechUnderstander.writeAudio(data, 0, data.length);
//        speechUnderstander.startUnderstanding(new SpeechUnderstanderListener() {
//            @Override
//            public void onVolumeChanged(int i, byte[] bytes) {
//
//            }
//
//            @Override
//            public void onBeginOfSpeech() {
//
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//
//            }
//
//            @Override
//            public void onResult(UnderstanderResult understanderResult) {
//                Log.i(TAG, "understandResult:" + understanderResult.getResultString());
//                Log.i(TAG, "tostring:" + understanderResult.toString());
//
//            }
//
//            @Override
//            public void onError(SpeechError speechError) {
//                Log.i(TAG, "onError" + speechError.getErrorDescription() + "code:" + speechError.getErrorCode());
//            }
//
//            @Override
//            public void onEvent(int i, int i1, int i2, Bundle bundle) {
//
//            }
//        });
    }

    public void startVoiceUnderstand(View view) {
        //唤醒AIUIAgent
        AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
        agent.sendMessage(wakeupMsg);

        //开始录音
        String param = "sample_rate=16000,data_type=audio";
        AIUIMessage audioMessage = new AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0, param, null);
        agent.sendMessage(audioMessage);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        agent.destroy();
    }
}
