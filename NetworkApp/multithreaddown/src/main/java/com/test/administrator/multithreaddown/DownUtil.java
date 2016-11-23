package com.test.administrator.multithreaddown;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/19.
 */
public class DownUtil {
    private String path;
    private String targetFile;
    private int threadNum;
    private DownThread[] threads;
    private int fileSize;
    public DownUtil(String path,String targetFile,int threadNum){
        this.path=path;
        this.threadNum = threadNum;
        threads = new DownThread[threadNum];
        this.targetFile = targetFile;

    }
    public void downLoad()throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty(
                "Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                        + "application/x-shockwave-flash, application/xaml+xml, "
                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                        + "application/x-ms-application, application/vnd.ms-excel, "
                        + "application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");

        //得到文件大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartSize = fileSize/threadNum +1;//将文件分块
        RandomAccessFile file = new RandomAccessFile(targetFile,"rw");
        file.setLength(fileSize);//设置本地文件大小
        file.close();
        for(int i=0;i<threadNum;i++){
            int startPos = i*currentPartSize;
            //每条线程使用一个RandomAccessFile进行下载
            RandomAccessFile currentPart = new RandomAccessFile(targetFile,"rw");
            currentPart.seek(startPos);//定位到该线程的下载位置
            threads[i] = new DownThread(startPos,currentPartSize,currentPart);
            threads[i].start();
        }
    }
    private class DownThread extends Thread{
        private int startPos;//当前线程的下载位置
        private int currentPartSize;//该线程负责下载的文件的大小
        private  RandomAccessFile currentPart;//当前线程负责下载的文件块
        public int length;//已下载的字节数
        public DownThread(int startPos,int currentPartSize,RandomAccessFile currentPart){
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }
        public void run(){
            try{
               URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                InputStream inStream = conn.getInputStream();
                //跳过startPos个字节，表示该线程只下载自己负责的那部分文件
                skipFully(inStream,this.startPos);
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                //读取网络数据，并写入文件中
                while(length<currentPartSize&&(hasRead=inStream.read(buffer))>0){
                    currentPart.write(buffer,0,hasRead);
                    length+=hasRead;
                }
                currentPart.close();
                inStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
    public double getCompleteRate(){
        int sumSize = 0;
        for(int i=0;i<threadNum;i++){
            sumSize +=threads[i].length;
        }
        return sumSize*1.0/fileSize;
    }
    public static void skipFully(InputStream in ,long bytes)throws IOException{
        long remaining  = bytes;
        long len =0;
        while(remaining>0){
            len = in.skip(remaining);
            remaining -=len;
        }
    }
}
