package cn.ssdut.lst.simple3d;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GLSurfaceView glView = new GLSurfaceView(this);
        MyRenderer renderer = new MyRenderer();
        glView.setRenderer(renderer);
        setContentView(glView);
    }
}
