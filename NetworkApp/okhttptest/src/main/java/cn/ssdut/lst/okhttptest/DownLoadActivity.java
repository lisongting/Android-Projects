package cn.ssdut.lst.okhttptest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 使用OkHttp的拦截器方式下载文件
 */
public class DownLoadActivity extends AppCompatActivity {

    //下载的URL
    private final String DOWNLOAD_URL="https://downpack.baidu.com/appsearch_AndroidPhone_1012271b.apk";
    private final String FILE_NAME="baidu.apk";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        mProgressBar = (ProgressBar) findViewById(R.id.id_pb_progress);
        //mProgressBar.setIndeterminate(false);
    }

    public void downLoad(View v) {
        //OkHttpClient client = new OkHttpClient();
        //使用这种构造方式创建OkHttpClient对象

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new ProgressResponseBody(response.body(),
                                new ProgressListenerImpl())).build();
                    }
                }).build();
        Request request = new Request.Builder()
                .url(DOWNLOAD_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(DownLoadActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Log.d("tag", "文件存储位置:" + path);
                    File file = new File(path,FILE_NAME);
                    FileOutputStream fos = new FileOutputStream(file);
                    InputStream is = response.body().byteStream();
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
                }
            }
        });

    }

    class ProgressListenerImpl implements ProgressListener{

        @Override
        public void onProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                }
            });
        }

        @Override
        public void onDone(long val) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DownLoadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
