package cn.ssdut.lst.ashmem;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.RemoteException;
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
    private MemoryFile mFile;
    private IMemoryService memoryStub;
    ServiceConnection conn = new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("tag","与远程服务建立连接");
            memoryStub = IMemoryService.Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.i("tag","与远程服务断开连接");
        }
    };
    @Override
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
        bt_read.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(memoryStub!=null){
                    try {
                        String tmp;
                        tmp = memoryStub.readValue();
                        Toast.makeText(MainActivity.this, "匿名共享内存中的值是：" +
                                tmp,Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bt_write.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(memoryStub!=null){
                    String str = et_edit.getText().toString();
                    if(str.length()!=0&&str!=null){
                        try {
                            memoryStub.setValue(Integer.parseInt(str));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"请输入要存放的值",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public String readValue() {
        int val =0;
        if(mFile!=null){
            byte[] buffer = new byte[4];
            try{
                mFile.readBytes(buffer, 0, 0, 4);
            }catch(Exception e){
                e.printStackTrace();
            }
            val = (buffer[0] << 24) | ((buffer[1] & 0xFF) << 16)
                    | ((buffer[2] & 0xFF) << 8) | (buffer[3] & 0xFF);
        }else{
            Log.i("tag", "file为NULL,获取值失败");
        }
        return String.valueOf(val);
    }
    public void setValue(int val) throws RemoteException {
        if (mFile == null) {
            Log.i("tag","mFile is NULL");
            return;
        }
        byte[] buffer = new byte[4];
        buffer[0] = (byte)((val>>>24) & 0xFF);
        buffer[1] = (byte)((val>>>16) & 0xFF);
        buffer[2] = (byte)((val>>>8) & 0xFF);
        buffer[3] = (byte)(val & 0xFF);
        try {
            mFile.writeBytes(buffer,0,0,4);
            Log.i("tag","成功地向匿名共享内存中写入数据："+val);
        } catch (Exception e) {
            Log.i("tag", "写入数据失败");
            e.printStackTrace();
        }
    }
}
