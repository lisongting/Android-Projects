package cn.ssdut.lst.volleytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String url_get="http://www.baidu.com";
    private String url_post="https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    private String url_pics = "http://pic20.nipic.com/20120409/9188247_091601398179_2.jpg";
    private ImageView show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (ImageView) findViewById(R.id.iv_show);
        File f;
    }
    public void doGet(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET
                , url_get
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("tag", s);
                    }
                }
                ,new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("tag", error.getMessage());
                    }
                }
        );
        requestQueue.add(stringRequest);
    }
    public void doPost(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener rightListener = new Response.Listener<String>() {
            public void onResponse(String s) {
                Log.i("tag",s);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", error.getMessage());
            }
        };
        //构建StringRequest对象
        StringRequest stringRequest = new StringRequest(Request.Method.POST
                , url_post,rightListener,errorListener){
            //将请求参数名与参数值放入map中并返回
            public Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                map.put("tel", "13655556666");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void downPicture(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener listener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                show.setImageBitmap(bitmap);
                writeToFileThread t = new writeToFileThread(bitmap);
                t.start();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error) {
                Log.i("tag", error.getMessage());
            }
        };
        ImageRequest request = new ImageRequest(url_pics
                ,listener
                ,0,0, ImageView.ScaleType.FIT_CENTER
                ,Bitmap.Config.RGB_565
                ,errorListener);
        requestQueue.add(request);
    }

    class writeToFileThread extends Thread{
        Bitmap picture;
        public writeToFileThread(Bitmap b){
            picture = b;
        }
        public void run(){
            Log.i("tag", "Environment.getExternalStorageState()：" + Environment.getExternalStorageState());
            Log.i("tag", "Environment.getExternalStorageDirectory()(sd卡？)的:" + Environment.getExternalStorageDirectory());
            File file = new File(Environment.getExternalStorageDirectory(),"123.jpg");
            Log.i("tag", "file的路径" + file.toString());
            Looper.prepare();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                //实践证明JPEG压缩格式的文件会比PNG压缩格式的更小
                picture.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                Toast.makeText(MainActivity.this,"图片下载成功",Toast.LENGTH_LONG).show();
                Log.i("tag", "图片写入成功");
                //picture.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Looper.loop();
        }
    }

}
