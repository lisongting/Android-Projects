package com.paperfish.viewsset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    BezierView bezierView;
    PercentCircleView percentCircleView;
    Button bt1, bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bezierView = (BezierView) findViewById(R.id.bezier_view1);
        percentCircleView = (PercentCircleView) findViewById(R.id.percentView);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bezierView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezierView.startAnim();
            }
        });

        percentCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentCircleView.startAnim();

            }
        });

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
    }


}
