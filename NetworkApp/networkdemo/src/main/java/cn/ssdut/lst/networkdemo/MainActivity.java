package cn.ssdut.lst.networkdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button bt_get;
    private Button bt_post;
    private Button bt_downPic;
    private Button bt_downMp3;
    private EditText et_edit;
    private TextView text_show;
    private ImageView pic_show;
    private   Handler handler;
    //淘宝网的电话号码归属地查询的web服务
    private String url_query="https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    //这是一张图片的下载链接
    private final String url_picture = "http://pic20.nipic.com/20120409/9188247_091601398179_2.jpg";
    //这是一首歌（歌名是《演员》）的下载链接
    private final String url_mp3 = "http://cc.stream.qqmusic.qq.com/102636799.m4a?fromtag=52";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_get = (Button) findViewById(R.id.bt_get);
        bt_post = (Button) findViewById(R.id.bt_post);
        bt_downPic = (Button) findViewById(R.id.bt_downPic);
        bt_downMp3 = (Button) findViewById(R.id.bt_downMp3);
        et_edit = (EditText) findViewById(R.id.et_edit);
        text_show = (TextView) findViewById(R.id.tv_show);
        pic_show = (ImageView) findViewById(R.id.iv_show);
        handler = new Handler(){
            public void handleMessage(Message msg){
                text_show.setText(msg.obj.toString());
            }
        };
        bt_get.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String phone = et_edit.getText().toString();
                if(phone.length()==11){
                    try {
                        doGet(phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this
                            ,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_post.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String phone = et_edit.getText().toString();
                if(phone.length()==11){
                    try {
                        doPost(phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bt_downPic.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                downLoadPicture();
            }
        });
        bt_downMp3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                downLoadMp3();
            }
        });
    }
    public void doGet(String phone)throws Exception {
        final URL url = new URL(url_query + "?tel=" + phone);
        GetThread t = new GetThread(handler, url);
        t.start();
    }
    public void doPost( String phone){
        final String phoneNum = phone;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener rightListener = new Response.Listener<String>() {
            public void onResponse(String s) {
                text_show.setText(s);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", error.getMessage());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.POST
                ,url_query,rightListener,errorListener){
            public Map<String,String> getParams(){
                Map<String, String> map = new HashMap<>();
                map.put("tel",phoneNum);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void downLoadPicture(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener listener = new Response.Listener<Bitmap>(){
            public void onResponse(Bitmap bitmap) {
                //获取到响应图片后，先将其显示在ImageView上
                pic_show.setImageBitmap(bitmap);
                //然后启动一个新线程将图片下载到文件
                PicToFileThread thread = new PicToFileThread(bitmap,MainActivity.this);
                thread.start();
            }
        };
        Response.ErrorListener errListener = new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", error.getMessage());
            }
        };
        //建立一个ImageRequest对象，其中有七个参数
        //url_picture:图片的URL
        //listener：正确响应结果的监听器
        //0,0：图片的最大宽度和最大高度。都设置为0表示默认
        //ImageView.ScaleType.FIT_CENTER:表示bitmap在ImageView中的布局属性
        //Bitmap.Config.RGB_565：图片的颜色属性，Bitmap.Config下的几个常量都可以在这里使用，
        //                       其中ARGB_8888每个图片像素占据4个字节的大小，而RGB_565
        //                       则表示每个图片像素占据2个字节大小
        //errListener:错误响应结果的监听器
        ImageRequest imageRequest = new ImageRequest(
                 url_picture
                ,listener
                ,0,0
                ,ImageView.ScaleType.FIT_CENTER
                ,Bitmap.Config.RGB_565
                ,errListener);
        //最后将ImageRequest加入到请求队列中
        queue.add(imageRequest);
    }
    public void downLoadMp3(){
        MyAsyncTask task = new MyAsyncTask(MainActivity.this);
        try {
            task.execute(new URL(url_mp3));
            Log.i("tag", "asyncTask已启动");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
