package cn.ssdut.lst.navigation;

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
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_locate,bt_navi;
    private EditText et_addr;
    private MapView mapView;
    private AMap aMap;
    private LocationManager locationManager;
    private GeocodeSearch geoSearch;
    private RouteSearch routeSearch;
    //标记用户是选择查找位置还是导航
    private boolean isLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.id_mv_map);
        mapView.onCreate(savedInstanceState);
        init();

    }

    //进行地址查找的监听器
    private class geoSearchListener implements GeocodeSearch.OnGeocodeSearchListener{
        //当地址反向解析（将经纬度转换为地点）时触发
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        }
        //当查询地址的经纬度（地址解析）时触发
        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            //如果当前用户选择的是查找定位
            if(isLocate){
                GeocodeAddress addr = geocodeResult.getGeocodeAddressList().get(0);
                //获取经纬度
                LatLonPoint pos = addr.getLatLonPoint();
                LatLng targetPos = new LatLng(pos.getLatitude(), pos.getLongitude());
                CameraUpdate cu = CameraUpdateFactory.changeLatLng(targetPos);
                //更新地图的显示区域
                aMap.moveCamera(cu);
                aMap.clear();
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(targetPos)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .draggable(true);
                aMap.addMarker(markerOptions);
                //添加圆形
                CircleOptions circle = new CircleOptions()
                        .center(targetPos)
                        .fillColor(0x80ffff00)
                        .radius(80)
                        .strokeWidth(1f)
                        .strokeColor(0xff000000);
                aMap.addCircle(circle);

            }else{//如果用户选择了导航
                GeocodeAddress addr = geocodeResult.getGeocodeAddressList().get(0);
                //获取目前的经纬度
                LatLonPoint latlng = addr.getLatLonPoint();
                //获取用户当前的位置
                Location loc=null;
                if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                //创建路径规划的起点
                RouteSearch.FromAndTo ft = new RouteSearch.FromAndTo(new LatLonPoint(loc.getLatitude(),
                        loc.getLongitude()),latlng);
                //创建自驾车的查询条件
                RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(
                        ft,//路径起点
                        RouteSearch.DRIVING_SINGLE_DEFAULT,
                        null,//该参数指定必须经过的多个点
                        null,//指定必须避开的多个区域
                        null//指定必须避开的道路
                );
                //进行异步计算
                routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
            }

        }
    }
    //路径查询的监听器
    private class routeSearchListener implements RouteSearch.OnRouteSearchListener{
        @Override
        public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
        }
        //查询到自驾车的路线
        @Override
        public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
            //获取系统规划的第一条路线,(实际上可以提供多条路径供用户选择)
            DrivePath drivePath = driveRouteResult.getPaths().get(0);
            //获取该规划路线所包含的多条路段
            List<DriveStep> steps = drivePath.getSteps();
            for (DriveStep step : steps) {
                //获取组成该路径的多个点
                List<LatLonPoint> points = step.getPolyline();
                List<LatLng> latLngs = new ArrayList<>();
                for (LatLonPoint point : points) {
                    latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
                }
                //向地图添加多线段
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(latLngs)
                        .color(0xffff0000)
                        .width(8);
                aMap.addPolyline(polylineOptions);
            }
        }
        @Override
        public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        }
        @Override
        public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
        }
    }
    //LocationListener，地理位置变化的监听器
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新地图显示的位置
            if (location != null) {
                updatePosition(location);
            }else{
                Toast.makeText(MainActivity.this,"location 为null",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @TargetApi(23)
        @Override
        public void onProviderEnabled(String provider) {
            if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                updatePosition(locationManager.getLastKnownLocation(provider));
            }
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    @TargetApi(23)
    private void init() {
        bt_locate = (Button) findViewById(R.id.id_bt_locate);
        bt_navi = (Button) findViewById(R.id.id_bt_navigate);
        bt_locate.setOnClickListener(this);
        bt_navi.setOnClickListener(this);
        et_addr = (EditText) findViewById(R.id.id_et_addr);


        //初始AMap
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置一个放大级别
            CameraUpdate cu = CameraUpdateFactory.zoomTo(15);
            aMap.moveCamera(cu);
        }

        geoSearch = new GeocodeSearch(MainActivity.this);
        routeSearch = new RouteSearch(MainActivity.this);
        //绑定监听器
        geoSearch.setOnGeocodeSearchListener(new geoSearchListener());
        routeSearch.setRouteSearchListener(new routeSearchListener());


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        }
    }

    @TargetApi(23)
    @Override
    public void onClick(View v) {
        String addr;
        switch (v.getId()) {
            case R.id.id_bt_locate:
                isLocate = true;
                addr = et_addr.getEditableText().toString();
                if (addr.length() == 0) {
                    Toast.makeText(MainActivity.this,"请输入位置",Toast.LENGTH_SHORT).show();
                }else{
                    //只能定位大连的
                    GeocodeQuery query = new GeocodeQuery(addr,"大连");
                    geoSearch.getFromLocationNameAsyn(query);
                }
                break;
            case R.id.id_bt_navigate:
                isLocate = false;
                addr = et_addr.getEditableText().toString();
                if (addr.length() == 0) {
                    Toast.makeText(MainActivity.this,"请输入位置",Toast.LENGTH_SHORT).show();
                }else{
                    //这里需要手动指定了城市
                    GeocodeQuery query = new GeocodeQuery(addr, "大连");
                    //根据地址执行异步查询
                    geoSearch.getFromLocationNameAsyn(query);
                }
                break;
            default:
                break;
        }
    }

    private void updatePosition(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
        //更新地图的显示区域
        aMap.moveCamera(cu);
        aMap.clear();
        MarkerOptions markerOptions = new MarkerOptions()
                .position(pos)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .draggable(true);
        aMap.addMarker(markerOptions);
    }
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mapView.onSaveInstanceState(savedInstanceState);
    }

    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
