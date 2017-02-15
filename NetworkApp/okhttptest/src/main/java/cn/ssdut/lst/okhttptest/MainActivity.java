package cn.ssdut.lst.okhttptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

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
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView) findViewById(R.id.tv_show);
    }
}
