package cn.ssdut.lst.matrix;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by lisongting on 2017/10/16.
 */

public class DecodeActivity extends AppCompatActivity {

    private Button bt1;
    private EditText editText;
    private ImageView imageView;
    private String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);
        bt1 = (Button) findViewById(R.id.decode);
        editText = (EditText) findViewById(R.id.edit);
        imageView = (ImageView) findViewById(R.id.image);

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
//            InputStream input = getAssets().open("base64.txt");
            InputStream input = getAssets().open("b.txt");
            BufferedInputStream bis = new BufferedInputStream(input);

            StringBuilder sb =new StringBuilder();
            byte[] data = new byte[1024];
            while (bis.read(data, 0, data.length) != -1) {
                sb.append(new String(data));
            }
            Log.i("tag", "length:" + sb.length() + "\n" + sb.toString());
            str = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (str.length() > 10) {
                        DecodeTask task = new DecodeTask(str);
                        FutureTask<Bitmap> future = new FutureTask<>(task);
                        new Thread(future).start();
                        final Bitmap tmp;
                        tmp = future.get(2, TimeUnit.SECONDS);
                        if (tmp != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(tmp);
                                }
                            });
                        } else {
                            Log.i("tag", "bitmap is null");
                        }
                    } else {
                        Toast.makeText(DecodeActivity.this, "长度太小", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class DecodeTask implements Callable<Bitmap> {
        private String base64;

        public DecodeTask(String str){
            base64 = str;
        }


        @Override
        public Bitmap call() throws Exception {
            Log.i("tag", "DecodeTask is running");
            return ImageUtils.decodeBase64ToBitmap(base64.trim());
        }
    }

}
