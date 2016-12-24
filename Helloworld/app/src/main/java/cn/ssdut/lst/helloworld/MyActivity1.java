package cn.ssdut.lst.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/21.
 * setResult之后的finish()方法会强行关闭Activity
 */
public class MyActivity1 extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity1);
        Toast.makeText(this, "taskId:"+this.getTaskId(), Toast.LENGTH_SHORT).show();

    }
    public void onBackPressed(){
        Intent t = new Intent();
        String tmp = "你好!";
        t.putExtra("info",tmp);
        setResult(1,t);
        finish();
    }
}
