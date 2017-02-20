package cn.ssdut.lst.propertyanim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.start);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                ObjectAnimator moveIn = ObjectAnimator.ofFloat(textView, "translationY", 0f, 400f);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
                ObjectAnimator fadeInout = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0.2f, 1f);
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(rotate).with(fadeInout).after(moveIn);
                animSet.setDuration(5000);
                animSet.start();
            }
        });
    }
}

