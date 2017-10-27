package cn.ssdut.lst.matrix;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by lisongting on 2017/10/9.
 */

public class MapViewTestActivity extends AppCompatActivity {

    private MapView mapView;

    String TAG = "tag";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texture_view_test);
        mapView = (MapView) findViewById(R.id.mapView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Bitmap bitmap = ImageUtils.decodeBase64ToBitmap(MainActivity.base64PNG);
        mapView.updateMap(bitmap);
    }

    public void log(String s) {
        Log.i(TAG, TAG + " -- "+s);
    }



}
