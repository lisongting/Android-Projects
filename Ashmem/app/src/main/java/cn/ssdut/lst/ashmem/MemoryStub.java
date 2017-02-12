package cn.ssdut.lst.ashmem;

import android.os.MemoryFile;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/12.
 */

public class MemoryStub extends IMemoryService.Stub {

    public MemoryFile file =null;
    public MemoryStub(){
        try{
            //先创建一个名为“ashmem”的MemoryFile对象，大小为4字节
            file = new MemoryFile("ashmem",4);
            setValue(0);
            Log.i("tag","匿名共享内存创建成功");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public MemoryFile getAshmemFile(){
        if(file!=null)
            return file;
        else
            return null;
    }
    public void setValue(int val)  {
        if (file == null) {
            Log.i("tag","file is NULL");
            return;
        }
        byte[] buffer = new byte[4];
        buffer[0] = (byte)((val>>>24) & 0xFF);
        buffer[1] = (byte)((val>>>16) & 0xFF);
        buffer[2] = (byte)((val>>>8) & 0xFF);
        buffer[3] = (byte)(val & 0xFF);
        try {
            file.writeBytes(buffer,0,0,4);
            Log.i("tag","成功地向匿名共享内存中写入数据："+val);
        } catch (Exception e) {
            Log.i("tag", "写入数据失败");
            e.printStackTrace();
        }
    }
}
