package com.test.administrator.httpclienttest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
public class MainActivity extends AppCompatActivity {
    private Button bt_get;
    private Button bt_post;
    private TextView show;
    //淘宝网的电话号码归属地查询的web服务器
    private String url_get =
            "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15850781443";
    private String url_post =
            "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    HttpURLConnection urlConn;
    private static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_get = (Button) findViewById(R.id.bt_get);
        bt_post = (Button) findViewById(R.id.bt_post);
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
}
