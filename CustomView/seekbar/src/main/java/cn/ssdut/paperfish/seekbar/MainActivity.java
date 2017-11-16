package cn.ssdut.paperfish.seekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bt1,bt2;
    PercentCircleView percentCircleView;
    CustomSeekBar seekBar1,seekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);

        percentCircleView = (PercentCircleView) findViewById(R.id.percentView);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentCircleView.setPercent(percentCircleView.getPercent()+6);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentCircleView.setPercent(percentCircleView.getPercent()-6);
            }
        });

//        seekBar2.setOnSeekChangeListener(new CustomSeekBar.OnProgressChangeListener() {
//            @Override
//            public void onProgressChanged(int value) {
//                log("OnSeekChangeListener onProgressChanged :" + value);
//            }
//
//            @Override
//            public void onProgressChangeCompleted(int value) {
//                log("OnSeekChangeListener onSeekCompleted :" + value);
//            }
//        });
    }

    private void log(String s) {
        Log.i("tag", s);
    }
}
