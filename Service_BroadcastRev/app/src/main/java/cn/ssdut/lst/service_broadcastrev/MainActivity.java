package cn.ssdut.lst.service_broadcastrev;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BindService.MyBinder binder;
    Intent intent;
    //定义一个ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection(){
        public void onServiceConnected(ComponentName name ,IBinder service){
            Log.d("tag","与service建立连接");
            //获取service的onBind方法所返回的MyBinder对象
            binder =  (BindService.MyBinder) service;
        }
        public void onServiceDisconnected(ComponentName name){

            Log.d("tag", "与service断开连接");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,BindService.class);
        //startService(intent);
    }

    public void bind(View view) {
        bindService(intent,conn, BindService.BIND_AUTO_CREATE);
    }
    public void unbind(View view) {
        unbindService(conn);
    }
    public void getState(View v){
        Toast.makeText(MainActivity.this, "service的count值是："+binder.getCount(), Toast.LENGTH_SHORT).show();
    }
}
