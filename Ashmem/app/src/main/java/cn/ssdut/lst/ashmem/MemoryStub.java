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
 * [困惑]
 * 我觉得我这个例子程序还不是本质上的匿名内存共享，
 * 因为这使用Aidl接口来访问stub中的MemoryFile对象，
 * 尽管是跨进程，但这并不是理想的匿名内存共享
 * 被困在：MemoryFile只有一个构造方法了。罗升阳那本书中的例子是
 * 通过文件描述符做参数，调用了MemoryFile的第二个构造方法，从而
 * 在另一个进程中还原出MemoryFile匿名共享内存对象
 *
 * 我想将在Aidl接口文件中写一个getMemoryFile()函数，用来返回那个
 * MemoryFile对象，而MemoryFile貌似不是可序列化的，结果aidl中不能
 * 返回MemoryFile对象。我不知道怎样用这个类变成自定义可序列化的类。
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
