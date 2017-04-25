package cn.ssdut.lst.tts;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Button bt_start,bt_stop;
    private EditText editText;
    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_start = (Button) findViewById(R.id.id_bt_speak);
        bt_stop = (Button) findViewById(R.id.id_bt_stop);
        editText = (EditText) findViewById(R.id.id_edittext);

        tts = new TextToSpeech(this, this);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tts != null && !tts.isSpeaking()) {
                    String text = editText.getText().toString();
                    tts.setPitch(0.0F);
                    tts.speak(text.subSequence(0,text.length()), TextToSpeech.QUEUE_FLUSH, null,"1");
                }

            }
        });

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tts != null && tts.isSpeaking()) {
                    tts.stop();
                }
            }
        });
    }

    @Override
    @TargetApi(21)
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.i("tag", "初始化成功.");
            int result = tts.setLanguage(Locale.US);
            List <TextToSpeech.EngineInfo> list = tts.getEngines();
            for (TextToSpeech.EngineInfo i : list) {
                Log.i("tag", i.toString());
            }
            Set<Locale> set = tts.getAvailableLanguages();
            for (Locale l : set) {
                Log.i("tag",l.getDisplayLanguage()+"  "+l.toString());

            }

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void onStop() {
        super.onStop();
        tts.stop();
        tts.shutdown();
    }
}
