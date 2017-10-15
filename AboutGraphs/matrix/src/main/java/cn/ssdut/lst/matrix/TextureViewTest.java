package cn.ssdut.lst.matrix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

/**
 * Created by lisongting on 2017/10/9.
 */

public class TextureViewTest extends AppCompatActivity {

    private MapView mapView;
    private Button zoomIn,zoomOut;
    String TAG = "tag";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texture_view_test);
        mapView = (MapView) findViewById(R.id.mapView);
        zoomIn = (Button) findViewById(R.id.zoomIn);
        zoomOut = (Button) findViewById(R.id.zoomOut);

    }
    public void log(String s) {
        Log.i(TAG, TAG + " -- "+s);
    }



}
