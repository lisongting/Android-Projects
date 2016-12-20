package cn.ssdut.lst.shader_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Shader[] shaders = new Shader[5];//5个着色器
    private int[] colors;
    MyView myView;//自定义视图类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = (MyView) findViewById(R.id.my_view);
        //获取bitmap的实例
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.water);
        colors = new int[]{Color.RED,Color.GREEN,Color.BLUE};//设置红绿蓝三种颜色的渐变
        //实例化shader
        shaders[0] = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        //线性渐变
        shaders[1] = new LinearGradient(0, 0, 100, 100, colors, null, Shader.TileMode.REPEAT);
        //圆形渐变
        shaders[2] = new RadialGradient(100, 100, 80, colors, null, Shader.TileMode.REPEAT);
        //角度渐变
        shaders[3] = new SweepGradient(160, 160, colors, null);
        //组合渐变
        shaders[4] = new ComposeShader(shaders[1], shaders[2], PorterDuff.Mode.DARKEN);
        Button bt1 = (Button) findViewById(R.id.bn1);
        bt1.setOnClickListener(this);
        Button bt2 = (Button) findViewById(R.id.bn2);
        bt2.setOnClickListener(this);
        Button bt3 = (Button) findViewById(R.id.bn3);
        bt3.setOnClickListener(this);
        Button bt4 = (Button) findViewById(R.id.bn4);
        bt4.setOnClickListener(this);
        Button bt5 = (Button) findViewById(R.id.bn5);
        bt5.setOnClickListener(this);
    }

    public void onClick(View source) {
        switch (source.getId()) {
            case R.id.bn1:
                myView.paint.setShader(shaders[0]);
                break;
            case R.id.bn2:
                myView.paint.setShader(shaders[1]);
                break;
            case R.id.bn3:
                myView.paint.setShader(shaders[2]);
                break;
            case R.id.bn4:
                myView.paint.setShader(shaders[3]);
                break;
            case R.id.bn5:
                myView.paint.setShader(shaders[4]);
                break;
        }
        myView.invalidate();
    }
}
