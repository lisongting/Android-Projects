package cn.ssdut.lst.remoteservicecreater;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private Button bt_localSerivice;
    private Button bt_visitLocalS;
    private Button bt_unBindLocalS;
    private Button bt_remoteService;
    private Button bt_unBindRemoteS;
    LocalService.MyBinder binder;
    private IMyAidlInterface remoteBinder;
    Intent intent;
    Intent intentRemote;
    //通过bindService()方式启动Service时，需要定义一个ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection(){
        public void onServiceConnected(ComponentName name,IBinder service) {
            Toast.makeText(MainActivity.this, "本地服务绑定成功"
                    , Toast.LENGTH_SHORT).show();
            Log.i("tag","MainActivity与LocalService建立连接");
            binder = (LocalService.MyBinder)service;
        }
        public void onServiceDisconnected(ComponentName name) {
            Log.i("tag","MainActivity与LocalService断开连接");
        }
    };
    private ServiceConnection connRemote = new ServiceConnection(){
        public void onServiceConnected(ComponentName name,IBinder service) {
            Log.i("tag","MainActivity与远程服务建立连接");
            try {
                remoteBinder = IMyAidlInterface.Stub.asInterface(service);
                String tmp = remoteBinder.getMessage();
                Toast.makeText(MainActivity.this, "接收到内容：" + tmp
                        , Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        public void onServiceDisconnected(ComponentName name) {
            Log.i("tag","MainActivity与远程服务断开连接");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_localSerivice = (Button) findViewById(R.id.bt_localS);
        bt_visitLocalS = (Button) findViewById(R.id.bt_useLocalS);
        bt_unBindLocalS = (Button) findViewById(R.id.bt_unBindLocalS);
        bt_remoteService = (Button) findViewById(R.id.bt_remoteS);
        bt_unBindRemoteS = (Button) findViewById(R.id.bt_unBindRemoteS);
        Log.i("tag","MainActivity所在的进程ID是:"+ Process.myPid());
        //给按钮绑定监听器
        bt_localSerivice.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, LocalService.class);
                bindService(intent,conn,LocalService.BIND_AUTO_CREATE);
            }
        });
        bt_visitLocalS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = binder.getCount();
                Toast.makeText(MainActivity.this,
                        "Service中计数器的值是："+count
                        ,Toast.LENGTH_SHORT).show();
            }
        });
        bt_unBindLocalS.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                unbindService(conn);
            }
        });
        bt_remoteService.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                intentRemote = new Intent();
                intentRemote.setAction("cn.lst.RemoteService");
                intentRemote.setPackage("cn.ssdut.lst.remoteservicecreater");
                bindService(intentRemote,connRemote,BIND_AUTO_CREATE);
            }
        });
        bt_unBindRemoteS.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                unbindService(connRemote);
            }
        });
    }
}
