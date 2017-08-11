package com.paperfish.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WaveView waveView;
    private TextView textView;
    private boolean isStarting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveView = (WaveView) findViewById(R.id.waveview);
        textView = (TextView) findViewById(R.id.textview);

        isStarting = waveView.isClickable();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarting) {
                    waveView.setEnabled(true);
                    isStarting = true;
                } else {
                    waveView.setEnabled(false);
                    isStarting = false;
                }
                textView.setText(textView.getText()+"\n"+"当前是否可点击："+waveView.isClickable());
            }
        });
//        waveView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isStarting) {
//                    waveView.startAnimation();
//                    isStarting = true;
//                } else {
//                    waveView.endAnimation();
//                    isStarting = false;
//                }
//            }
//        });


    }
}
