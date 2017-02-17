package cn.ssdut.lst.networkdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/17.
 */

public class PicToFileThread extends Thread{
    private Bitmap bitmap;
    private Context context;
    public PicToFileThread(Bitmap bm,Context ctx){
        bitmap = bm;
        context = ctx;
    }
    public void run() {
        File file = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //compress()函数：按照jpeg格式将Bitmap压缩存放到文件输出流
            //第二个参数是压缩率，可取0-100,0表示压缩到最小，100表示保留最大的图像质量
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(context,"图片下载成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
