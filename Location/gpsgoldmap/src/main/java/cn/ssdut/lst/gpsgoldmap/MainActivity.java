package cn.ssdut.lst.gpsgoldmap;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    private LocationManager locationManager;
    private RadioButton rb_manual;
    private RadioButton rb_gps;
    @Override
    @TargetApi(23)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        init();
        rb_manual  = (RadioButton) findViewById(R.id.manual);
        rb_gps = (RadioButton) findViewById(R.id.gps);
        Button bn = (Button) findViewById(R.id.loc);
        final TextView latTv = (TextView) findViewById(R.id.lat);
        final TextView lngTv = (TextView) findViewById(R.id.lng);

        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MainActivity.this,"location changed",Toast.LENGTH_SHORT).show();
                updatePosition(location);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            @TargetApi(23)
            public void onProviderEnabled(String provider) {
                if(PackageManager.PERMISSION_GRANTED==checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(MainActivity.this,"location Provider Enable",Toast.LENGTH_SHORT).show();
                    updatePosition(locationManager.getLastKnownLocation(provider));
                }
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };


        bn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String lng = lngTv.getEditableText().toString().trim();
                String lat = latTv.getEditableText().toString().trim();

                if(rb_manual.isChecked()){
                    double dLng  = Double.parseDouble(lng);
                    double dLat = Double.parseDouble(lat);
                    //封装成一个LatLng对象
                    LatLng pos = new LatLng(dLat,dLng);
                    //创建一个显式经纬度的CameraUpdate
                    CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
                    //更新地图的显示区域
                    aMap.moveCamera(cu);
                    //创建MarkerOptions对象，
                    MarkerOptions markerOptions = new MarkerOptions();
                    //设置MarkerOptions的添加位置
                    markerOptions.position(pos);
                    markerOptions.title("定位点");
                    markerOptions.snippet("这就是根据你输入的经纬度查到的地点");
                    //设置MarkerOptions的图标
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    markerOptions.draggable(true);
                    //添加MarkerOption
                    Marker marker = aMap.addMarker(markerOptions);
                    //设置默认显示信息窗
                    marker.showInfoWindow();
                }
                if (rb_gps.isChecked()) {
                    if(PackageManager.PERMISSION_GRANTED==checkSelfPermission("android.permission.ACCESS_FINE_LOCATION")){

                        //之前这里使用了GPS_PROVIDER，但是总是不能正确的定位
                        //后来这里改为NETWORK_PROVIDER，才能正常使用定位。
                        //注：NETWORK_PROVIDER要在GPS和网络同时打开时才能使用
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listener);

                        //错误标记：这里getLastKownLocation()，如果使用LocationManager.GPS_PROVIDER或者NETWORK_PROVIDER
                        //则返回的Location对象总是为null。（不知道是为什么）
                        /*
                        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc == null) {
                            Toast.makeText(MainActivity.this,"Location 为null",Toast.LENGTH_SHORT).show();
                        }else{
                            updatePosition(loc);
                        }
                        */
                    }
                }
            }
        });
    }
    private void updatePosition(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        //设置一个创建经纬度的CameraUpdate
        CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
        //更新地图的显示区域
        aMap.moveCamera(cu);
        //清除所有Marker等覆盖物
        //aMap.clear();

        //创建MarkerOptions对象
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pos);
        //使用自定义的图标
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.triblegrass));
        markerOptions.title("你的位置");
        markerOptions.draggable(true);
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            //创建一个设置放大级别的CameraUpdata
            CameraUpdate cu = CameraUpdateFactory.zoomTo(15);
            //设置地图的默认放大级别
            aMap.moveCamera(cu);
            //创建一个更改地图倾斜度的CameraUpdate
            CameraUpdate tiltUpdate = CameraUpdateFactory.changeTilt(30);
            //改变地图的倾斜度
            aMap.moveCamera(tiltUpdate);
        }
    }
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
