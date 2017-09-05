package cn.ssdut.lst.okhttptest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView) findViewById(R.id.tv_show);
    }
    public void doGet(View view) {
        //1.构造okHttpClient实例
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("http://www.baidu.com").build();
        //3.将request封装为call,Call可以看做是请求的执行者
        Call call = okHttpClient.newCall(request);

        //4.执行call
        // Response response = call.execute();//execute()表示执行请求，返回一个Response对象
        Log.i("tag", "当前线程：" + Thread.currentThread().getId());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("tag",e.getMessage());
                e.printStackTrace();
            }

            //onResponse()的回调并不是在UI线程中，而是在子线程中的
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i("tag", "当前线程：" + Thread.currentThread().getId());
                Log.i("tag", "message:" + response.message());
                Log.i("tag", "response code:" + response.code());
                Log.i("tag", "isSuccessful:" + response.isSuccessful());
                Log.i("tag", "protocol:" + response.protocol());
                Log.i("tag", "response body:" + response.body().string());

            }
        });

    }
    //[示例]使用Post方式发送Json数据
    public void postWithJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username","test");
            jsonObject.put("password","12346");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString = jsonObject.toString();

        //构造请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("一个URL")
                //把请求体放入到Request中
                .post(requestBody)
                .build();
        //enqueue是一种异步请求方式，不会阻塞UI线程
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //也可以对服务器返回来的Json进行解析
                    String res = response.body().string();
                    try {
                        JSONObject object = new JSONObject(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //然后下面就可以调用json的一系列get方法了
                }
            }
        });
    }

    //文件下载示例代码,这种下载方式比较死板，不利于扩展。因此应该使用拦截器的方式
    public void downLoad(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("文件下载的URL")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is;
                FileOutputStream fos;
                if (response.isSuccessful()) {
                    is = response.body().byteStream();

                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File file = new File(path,"文件名.后缀");
                    fos = new FileOutputStream(file);
                    byte[] bytes = new byte[1024];
                    int len =0;
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes);
                    }
                    if(is!=null){
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                    //注：可以使用response.body().contentLength()获取文件的大小
                    //再结合Handler和ProgressBar就可以实现实时的进度反馈
                }



            }
        });
    }



}
