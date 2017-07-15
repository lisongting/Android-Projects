package com.test.administrator.uitest1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    YoutuConnection youtuConnection;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageview);
        Handler handler = new Handler();
        youtuConnection= new YoutuConnection(this, handler);
    }

    public void getImage(View view) {

        youtuConnection.getUserFaceBitmap("e69d8ee69dbee5bbb7", new YoutuConnection.Callback() {
            @Override
            public void onImageReady(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        });


    }
}
