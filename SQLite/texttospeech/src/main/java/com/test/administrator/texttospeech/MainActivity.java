package com.test.administrator.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this,new TextToSpeech.OnInitListener(){
            public void onInit(int status){
                //如果装载tts引擎成功
                if(status==TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.US);
                    if(result!=TextToSpeech.LANG_COUNTRY_AVAILABLE
                            &&result!=TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(MainActivity.this, "tts不支持的语言", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        editText = (EditText)findViewById(R.id.editText);
    }
    public void read(View source){
        tts.speak(editText.getText().toString(),TextToSpeech.QUEUE_ADD,null,"speech");
    }
    public void record(View source){

    }
}
