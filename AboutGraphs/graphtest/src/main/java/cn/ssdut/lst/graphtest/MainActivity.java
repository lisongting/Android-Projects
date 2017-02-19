package cn.ssdut.lst.graphtest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.start);
        b2 = (Button) findViewById(R.id.stop);
        imageView = (ImageView) findViewById(R.id.imageView);
        //获取AnimationDrawable动画对象
        final AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                anim.start();
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                anim.stop();
            }
        });
    }

}
