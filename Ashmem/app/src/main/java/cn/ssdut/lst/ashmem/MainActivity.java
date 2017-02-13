package cn.ssdut.lst.ashmem;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bt_read;
    private Button bt_write;
    private EditText et_edit ;
    private IMemoryService memoryService;
    ServiceConnection conn = new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("tag","与远程服务建立连接");
            memoryService = IMemoryService.Stub.asInterface(service);
        }
        public void onServiceDisconnected(ComponentName name) {
            Log.i("tag","与远程服务断开连接");
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_read = (Button) findViewById(R.id.bt_read);
        bt_write = (Button) findViewById(R.id.bt_write);
        et_edit = (EditText) findViewById(R.id.et_edit);
        Intent intent = new Intent();
        intent.setAction("cn.lst.ashmem.service");
        intent.setPackage("cn.ssdut.lst.ashmem");
        bindService(intent, conn, BIND_AUTO_CREATE);
        Log.i("tag", "MainActivity的进程id:" + Process.myPid());
        bt_read.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                    try {
                        String tmp;
                        tmp = memoryService.readValue();
                        Toast.makeText(MainActivity.this,
                                "匿名共享内存中的值是：" +
                                tmp,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
        bt_write.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                    String str = et_edit.getText().toString();
                    if(str.length()!=0&&str!=null){
                        try {
                            memoryService.setValue(Integer.parseInt(str));
                            Toast.makeText(MainActivity.this,"写入成功",Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(MainActivity.this
                                ,"请输入要存放的值",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
