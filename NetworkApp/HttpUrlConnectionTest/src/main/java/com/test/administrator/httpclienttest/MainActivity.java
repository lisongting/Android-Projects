package com.test.administrator.httpclienttest;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private Button bt_get;
    private Button bt_post;
    private Button bt_downLoad;
    private TextView show;
    //淘宝网的电话号码归属地查询的web服务器
    private String url_get =
            "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15850781443";
    private String url_post =
            "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    private String url_mp3 =
            "http://cc.stream.qqmusic.qq.com/102636799.m4a?fromtag=52";
    HttpURLConnection urlConn;
    private static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_get = (Button) findViewById(R.id.bt_get);
        bt_post = (Button) findViewById(R.id.bt_post);
        bt_downLoad = (Button) findViewById(R.id.bt_download);
        show = (TextView) findViewById(R.id.tv_show);
        handler = new Handler(){
            public void handleMessage(Message msg) {
                    show.setText(msg.obj.toString());
            }
        };
        bt_get.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //因为进行http通信属于耗时操作
                // 因此启动新线程访问
                Thread t = new Thread(new GetThread());
                t.start();
            }
        });
        bt_post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread t = new Thread(new PostThread());
                t.start();
            }
        });
        bt_downLoad.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                downMp3Thread thread = new downMp3Thread();
                thread.start();
            }
        });

    }
    class GetThread implements Runnable{
        public void run() {
            try {
                URL url = new URL(url_get);
                urlConn = (HttpURLConnection) url.openConnection();
                //HttpURLConnection默认就是用GET发送请求，
                // 所以下面的setRequestMethod可以省略
                urlConn.setRequestMethod("GET");
                //设置支持从服务端读取输入流
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                //设置8秒的超时计时，如果8秒未连接服务器，则超时
                urlConn.setConnectTimeout(8000);
                InputStream is = urlConn.getInputStream();
                if (urlConn.getResponseCode()==200) {
                    int hasRead=0;
                    byte[] buffer = new byte[128];
                    StringBuilder sb =new StringBuilder();
                    while ((hasRead = is.read(buffer)) != -1) {
                        String tmp =new String(buffer, 0, hasRead,"gbk");
                        sb.append(tmp);
                    }
                    //向主线程的handler发送一个消息，让其更新UI
                    Message msg = handler.obtainMessage();
                    //将从网页查询得到的字符串通过Message发送给主线程
                    msg.obj = sb.toString();
                    handler.sendMessage(msg);
                }else{
                    Log.i("tag","状态码不是200");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class PostThread implements Runnable{
        public void run() {
            try {
                URL url = new URL(url_post);
                urlConn =(HttpURLConnection) url.openConnection();
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                //设置为POST方式
                urlConn.setRequestMethod("POST");
                String content = "tel=" + URLEncoder.encode("18640871022", "gbk");
                DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
                out.writeBytes(content);
                out.flush();
                out.close();
                InputStream is = urlConn.getInputStream();

                if (urlConn.getResponseCode()==200) {
                    int hasRead=0;
                    byte[] buffer = new byte[128];
                    StringBuilder sb =new StringBuilder();
                    while ((hasRead = is.read(buffer)) != -1) {
                        String tmp =new String(buffer, 0, hasRead,"gbk");
                        sb.append(tmp);
                    }
                    Log.i("tag", "接收到来自服务器的信息：" + sb.toString());
                    //向主线程的handler发送一个消息，让其更新UI
                    Message msg = handler.obtainMessage();
                    //将从网页查询得到的字符串通过Message发送给主线程
                    msg.obj = sb.toString();
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class downMp3Thread extends Thread{
        private File music;
        int fileSize;//文件大小
        int hasDown=0;//已经下载的大小
        public void run() {
            try {
                music = new File(Environment.getExternalStorageDirectory(), "薛之谦-演员.mp3");
                URL url = new URL(url_mp3);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(8000);
                conn.setUseCaches(true);
                if (conn.getResponseCode() == 200) {
                    fileSize = conn.getContentLength();
                    Log.i("tag","文件大小:"+fileSize+"字节");
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(music);
                    byte[] buffer = new byte[2048];
                    int hasRead = 0;
                    while((hasRead = is.read(buffer)) != -1){
                        fos.write(buffer, 0, hasRead);
                        hasDown+=hasRead;
                        NumberFormat percent =java.text.NumberFormat.getPercentInstance();
                        Log.i("tag", "写入了：" + hasRead + "字节;当前进度"+percent.format((float)hasDown/fileSize));
                    }
                    fos.flush();
                    fos.close();
                    Log.i("tag", "文件下载成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
