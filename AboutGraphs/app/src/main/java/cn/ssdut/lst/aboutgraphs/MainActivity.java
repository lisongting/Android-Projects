package cn.ssdut.lst.aboutgraphs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new MyView(this));
        Drawable d;
    }

    public boolean onTouchEvent(MotionEvent event) {
        //获取MotionEvent的事件ID
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("info", "ACTION_DOWN");
                //do something
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("info", "ACTION_MOVE");
                //do something
                break;
            case MotionEvent.ACTION_UP:
                Log.i("info", "ACTION_UP");
                //do something
                break;
        }
        return true;
    }
}
