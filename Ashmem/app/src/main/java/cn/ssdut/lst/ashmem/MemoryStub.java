package cn.ssdut.lst.ashmem;

import android.os.MemoryFile;
import android.util.Log;
/**
 *Created by Administrator on 2017/2/12.
 * [记录]
 * 当Service和MainActivity运行在不同进程时，
 * 无法使用MemoryFile file = MemoryStub.File的方式
 * 获取到MemoryStub的静态成员变量。
 * 只有在同一个进程中才可以
 */
public class MemoryStub extends IMemoryService.Stub {
    public static MemoryFile file;
    public MemoryStub(){
        try{
            //先创建一个名为“ashmem”的MemoryFile对象，大小为4字节
            this.file = new MemoryFile("ashmem",4);
            setValue(0);
            Log.i("tag","匿名共享内存创建成功");
            Log.i("tag", "匿名共享内存大小："+file.length());
            Log.i("tag", "是否允许被清理："+file.isPurgingAllowed());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setValue(int val)  {
        if (file == null) {
            Log.i("tag","file is NULL");
            return;
        }
        byte[] buffer;
        buffer = String.valueOf(val).getBytes();
        try {
            file.writeBytes(buffer,0,0,buffer.length);
            Log.i("tag","成功地向匿名共享内存中写入数据："+val);
        } catch (Exception e) {
            Log.i("tag", "写入数据失败");
            e.printStackTrace();
        }
    }
    public String readValue() {
        String str=null;
        if(file!=null){
            byte[] buffer = new byte[4];
            try{
                file.readBytes(buffer, 0, 0, buffer.length);
            }catch(Exception e){
                e.printStackTrace();
            }
            str = new String(buffer);
        }else{
            Log.i("tag", "file为NULL,获取值失败");
        }
        return str;
    }

}
