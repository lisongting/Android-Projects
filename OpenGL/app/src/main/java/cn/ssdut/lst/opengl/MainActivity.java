package cn.ssdut.lst.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        GLSurfaceView glView = new GLSurfaceView(this);
//        MyRenderer renderer = new MyRenderer();
//        Triangle triangle = new Triangle();
//        glView.setRenderer(triangle);
        setContentView(glView);
    }
}
